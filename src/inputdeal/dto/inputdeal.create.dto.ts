import { IsNotEmpty } from "class-validator";
import { ItemCreateDto } from "src/item/dto/item.create.dto";

export class InputDealCreateDto {
  @IsNotEmpty()
  nameOfDeal: string;
  items: ItemCreateDto[];
}