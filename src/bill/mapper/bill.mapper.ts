import { Bill } from "../entity/bill.entity";
import { BillDto } from "../dto/bill.dto";
import { BillCreateDto } from "../dto/bill.create.dto";
import { createDtoToItemEntity } from "../../item/mapper/item.mapper";
import { createDtoToInputDealEntity } from "../../inputdeal/mapper/inputdeal.mapper";

export const toBillDto = (bills: Bill[]): BillDto[] => {
  let billsDto: BillDto[] = bills.map(bill => {
    const billDto = new BillDto();
    billDto.totalBill = bill.totalBill;

    return billDto;
  });

  return billsDto;
}

export const toBillEntity = (createBill: BillCreateDto, composedTotalBill: string): Bill => {
  let bill = new Bill();
  bill.items = createBill.items.map(item => createDtoToItemEntity(item));
  bill.inputDeals = createBill.inputDeals !== undefined ? createBill.inputDeals.map(inputDeal => createDtoToInputDealEntity(inputDeal)) : [];
  bill.totalBill = composedTotalBill;
  
  return bill;
}