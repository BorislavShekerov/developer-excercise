import { ValueType } from "src/item/enum/value.type.enum";
import { ItemCreateDto } from "src/item/dto/item.create.dto";

export class TotalBill {
  billPair: Map<ValueType, number>;
  remainderOfItems: ItemCreateDto[];
}