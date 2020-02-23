import { Injectable, HttpException, HttpStatus, flatten } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Bill } from "../entity/bill.entity";
import { Repository, In } from "typeorm";
import { BillDto } from "../dto/bill.dto";
import { toBillDto, toBillEntity } from "../mapper/bill.mapper";
import { BillCreateDto } from "../dto/bill.create.dto";
import { ItemCreateDto } from "src/item/dto/item.create.dto";
import { Item } from "src/item/entity/item.entity";
import { InputDealCreateDto } from "src/inputdeal/dto/inputdeal.create.dto";
import { InputDeal } from "src/inputdeal/entity/inputdeal.entity";
import { ValueType } from "src/item/enum/value.type.enum";
import { TotalBill } from "../dto/total.bill.dto";

@Injectable()
export class BillService {
  constructor(
    @InjectRepository(Bill)
    private readonly billRepo: Repository<Bill>,
    @InjectRepository(Item)
    private readonly itemRepo: Repository<Item>,
    @InjectRepository(InputDeal)
    private readonly inputDealRepo: Repository<InputDeal>
  ) {}

  async getAllBills(): Promise<BillDto[]> {
    const bills = await this.billRepo.find();
    return toBillDto(bills);
  }

  async getOneBill(id: string): Promise<BillDto> {
    const bill = await this.billRepo.findOne({
      where : { id }
    });
    
    this.isBillExist(bill);

    return toBillDto([bill])[0]; 
  }

  async createBill(createBill: BillCreateDto): Promise<BillDto> {
    const { items, inputDeals } = createBill;
    let composedTotalBill: string;
    let totalBill: number;
    
    if (items.length == 0) {
      throw new HttpException(`No items in the basket`, HttpStatus.BAD_REQUEST);
    }
 
    this.areItemsExist(items);
    this.areInputDealsExist(inputDeals);

    if (!inputDeals || inputDeals.length === 0) {
      totalBill = this.calculateTotalBill(items);
    } 
    else {
      totalBill = this.calculateTotalBillWithDiscounts(items, inputDeals);
    }

    
    composedTotalBill = this.composeTotalBill(totalBill);

    const bill: Bill = toBillEntity(createBill, composedTotalBill);

    await this.billRepo.save(bill);
    const billDto = new BillDto();
    billDto.totalBill = composedTotalBill;

    return billDto;
  }

  private composeTotalBill(totalBill: number): string {
    let composedTotalBill: string;
    let awsBill: number = Math.floor(totalBill);
    let cloudBill: number = parseInt((parseFloat((totalBill % 1).toFixed(2)) * 100).toFixed(0));
    
    if (cloudBill !== 0 && awsBill !== 0) {
      composedTotalBill = `${awsBill} aws and ${cloudBill} clouds`;
    }
    else if (cloudBill === 0 && awsBill !== 0) {
      composedTotalBill = `${awsBill} aws`;
    }
    else if (cloudBill !== 0 && awsBill === 0) {
      composedTotalBill = `${cloudBill} clouds`;
    }
   
    return composedTotalBill;
  }

  private calculateTotalBillWithDiscounts(
    items: ItemCreateDto[],
    inputDeals: InputDealCreateDto[]
    ): number {

      let totalBill: number = 0;
      let awsBill: number = 0;
      let cloudBill: number = 0;
      let billPair = new Map<ValueType, number>();
      let bill: TotalBill;

      if (this.containsInputDeal(inputDeals, "2 for 3") && items.length > 2) {
        bill = this.calculate2Of3(items, inputDeals);
        billPair = bill.billPair;
        items = bill.remainderOfItems; 
      }
      console.log(items);
      if (this.containsInputDeal(inputDeals, "buy 1 get 1 half price") && items.length > 1) {
        bill = this.calculateBuyOneGetSecondHalfPrice(items, inputDeals);
        billPair.set(ValueType.CLOUD, billPair.get(ValueType.CLOUD) + bill.billPair.get(ValueType.CLOUD));
        billPair.set(ValueType.AWS, billPair.get(ValueType.AWS) + bill.billPair.get(ValueType.AWS));
        items = bill.remainderOfItems;
      }
   
      cloudBill = billPair.get(ValueType.CLOUD) !== undefined ? billPair.get(ValueType.CLOUD) : 0;
      awsBill = billPair.get(ValueType.AWS) !== undefined ? billPair.get(ValueType.AWS) : 0;
 
      if (cloudBill !== 0) {
        cloudBill /= 100;
      }
      
      totalBill = awsBill + cloudBill;
      
      
      totalBill += this.calculateTotalBill(items);
      if (this.containsInputDeal(inputDeals, "Loyalty Card 10% discount")) {
        totalBill -= (totalBill * 0.1)
      }
      
      return totalBill;
    }

  private calculateBuyOneGetSecondHalfPrice(items: ItemCreateDto[], inputDeals: InputDealCreateDto[]): TotalBill {
    const awsCloudPairBill = new Map<ValueType, number>();
    let awsBill: number = 0;
    let cloudBill: number = 0;
    const totalBill = new TotalBill();

    const inputDealsItems: ItemCreateDto[] = flatten(inputDeals.filter(inputDeal => inputDeal.nameOfDeal === "buy 1 get 1 half price")
                                      .map(inputDeal => inputDeal.items));

    let intersectionOfItems: ItemCreateDto[] = items.filter((item => inputDealsItems.some(inputDeal => inputDeal.name === item.name)));
    
    intersectionOfItems[1].value = intersectionOfItems[1].value / 2;
    
    intersectionOfItems.forEach(item => {
      const indexOfElement = items.indexOf(item);
      items.splice(indexOfElement, 1);
      
    });

    intersectionOfItems.forEach(item => {
      if (item.valueType === ValueType.CLOUD) {
        cloudBill += item.value;
      } 
      else if (item.valueType === ValueType.AWS){
        awsBill += item.value;
      }
    });

    totalBill.remainderOfItems = items;

    awsCloudPairBill.set(ValueType.CLOUD, cloudBill);
    awsCloudPairBill.set(ValueType.AWS, awsBill);

    totalBill.billPair = awsCloudPairBill;
    return totalBill;
  }

  private calculate2Of3(items: ItemCreateDto[], inputDeals: InputDealCreateDto[]): TotalBill {
    const awsCloudPairBill = new Map<ValueType, number>();
    let awsBill: number = 0;
    let cloudBill: number = 0;
    const totalBill = new TotalBill();

    const inputDealsItems: ItemCreateDto[] = flatten(inputDeals.filter(inputDeal => inputDeal.nameOfDeal === "2 for 3")
                                      .map(inputDeal => inputDeal.items));
                                      
                                      
    let intersectionOfItems: ItemCreateDto[] = items.filter((item => inputDealsItems.some(inputDeal => inputDeal.name === item.name)));
    //const differenceOfItems = items.filter(item => !inputDealsItems.includes(item));
  
    if (intersectionOfItems.length > 3) {
      
      intersectionOfItems = intersectionOfItems.slice(0,3);
      
      intersectionOfItems.forEach(item => {
        const indexOfElement = items.indexOf(item);
        items.splice(indexOfElement, 1);
        
      });

      
      totalBill.remainderOfItems = items;
      
      const smallestCloudValue = Math.min(...intersectionOfItems.filter(item => item.valueType === ValueType.CLOUD).map(item => item.value));
      const smalllestAwsValue = Math.min(...intersectionOfItems.filter(item => item.valueType === ValueType.AWS).map(item => item.value));
      
      intersectionOfItems.forEach(item => {
        if (item.valueType === ValueType.CLOUD) {
          cloudBill += item.value;
        } 
        else if (item.valueType === ValueType.AWS){
          awsBill += item.value;
        }
      });
      
      cloudBill -= isFinite(smallestCloudValue) ? smallestCloudValue : 0;
      awsBill -= isFinite(smalllestAwsValue) ? smalllestAwsValue : 0;
      
      awsCloudPairBill.set(ValueType.CLOUD, cloudBill);
      awsCloudPairBill.set(ValueType.AWS, awsBill);
      
      totalBill.billPair = awsCloudPairBill;
    }
    else {
      
      intersectionOfItems.forEach(item => {
        const indexOfElement = items.indexOf(item);
        items.splice(indexOfElement, indexOfElement -1);
      });

      totalBill.remainderOfItems = items;
      
      const smallestCloudValue = Math.min(...intersectionOfItems.filter(item => item.valueType === ValueType.CLOUD).map(item => item.value));
      const smalllestAwsValue = Math.min(...intersectionOfItems.filter(item => item.valueType === ValueType.AWS).map(item => item.value));

      intersectionOfItems.forEach(item => {
        if (item.valueType === ValueType.CLOUD) {
          cloudBill += item.value;
        } 
        else if (item.valueType === ValueType.AWS){
          awsBill += item.value;
        }
      });

      cloudBill -= isFinite(smallestCloudValue) ? smallestCloudValue : 0;
      awsBill -= isFinite(smalllestAwsValue) ? smalllestAwsValue : 0;

      awsCloudPairBill.set(ValueType.CLOUD, cloudBill);
      awsCloudPairBill.set(ValueType.AWS, awsBill);

      totalBill.billPair = awsCloudPairBill;
    }
    
    return totalBill;
  }

  private containsInputDeal(inputDeals: InputDealCreateDto[], nameOfDeal: string): boolean {
    return inputDeals.some(inputDeal => inputDeal.nameOfDeal === nameOfDeal);
  }

  private calculateTotalBill(items: ItemCreateDto[]): number {
    let awsBill: number = 0;
    let cloudBill: number = 0;
    let totalBill: number = 0;

    items.forEach(item => {
      if (item.valueType === ValueType.CLOUD) {
        cloudBill += item.value;
      } 
      else if (item.valueType === ValueType.AWS){
        awsBill += item.value;
      }
    });
   
    if (cloudBill !== 0) {
      cloudBill /= 100;
    }

    totalBill = awsBill + cloudBill;
    
    return totalBill;
  }

  private isBillExist(bill: Bill) {
    if (!bill) {
      throw new HttpException(`The bill doesn't exist`, HttpStatus.BAD_REQUEST);
    }
  }

  private async areItemsExist(itemsCreateDto: ItemCreateDto[]) {
    const items = await this.itemRepo.find({
      where: { name: In(itemsCreateDto.map(itemDto => itemDto.name))}
    });

    if (!items) {
      throw new HttpException(`Items do not exist`, HttpStatus.BAD_REQUEST);
    }
  }

  private async areInputDealsExist(inputDealsCreateDto: InputDealCreateDto[]) {
    if (inputDealsCreateDto !== undefined && inputDealsCreateDto.length !== 0) {
      const inputDeals = await this.inputDealRepo.find({
        where: { nameOfDeal: In(inputDealsCreateDto.map(inputDeal => inputDeal.nameOfDeal))}
      });
  
      if (!inputDeals) {
        throw new HttpException(`Input deals do not exist`, HttpStatus.BAD_REQUEST);
      }
    }
  }
}