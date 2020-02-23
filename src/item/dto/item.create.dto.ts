import { IsNotEmpty, IsEnum } from 'class-validator'; 
import { ValueType } from '../enum/value.type.enum';

export class ItemCreateDto {
  @IsNotEmpty()
  name: string;

  @IsNotEmpty()
  value: number;

  @IsEnum(ValueType)
  valueType: ValueType
}