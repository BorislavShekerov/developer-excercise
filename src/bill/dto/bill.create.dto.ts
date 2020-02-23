import { ItemCreateDto } from "src/item/dto/item.create.dto";
import { InputDealCreateDto } from "src/inputdeal/dto/inputdeal.create.dto";

export class BillCreateDto {
  items: ItemCreateDto[];
  inputDeals: InputDealCreateDto[];
}