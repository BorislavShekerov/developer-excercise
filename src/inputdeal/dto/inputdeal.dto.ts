import { IsNotEmpty } from "class-validator";

export class InputDealDto {
  @IsNotEmpty()
  id: string;

  @IsNotEmpty()
  nameOfDeal: string;
}