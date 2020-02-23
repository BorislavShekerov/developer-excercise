import { Injectable, HttpException, HttpStatus } from "@nestjs/common";
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
import { calculateTotalBill, calculateTotalBillWithDiscounts, composeTotalBill } from "../shared/bill.util";

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
      totalBill = calculateTotalBill(items);
    } 
    else {
      totalBill = calculateTotalBillWithDiscounts(items, inputDeals);
    }
    
    composedTotalBill = composeTotalBill(totalBill);

    let bill: Bill = toBillEntity(createBill, composedTotalBill);
    bill = await this.billRepo.save(bill);
    const billDto = toBillDto([bill])[0];

    return billDto;
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

  async deleteBill(id: string): Promise<void> {
    const bill: Bill = await this.billRepo.findOne({ where: { id }});
    this.isBillExist(bill);
    await this.billRepo.delete({ id });
  }
}