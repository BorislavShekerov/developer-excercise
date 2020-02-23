import { IsNotEmpty } from 'class-validator';
import { ValueType } from '../enum/value.type.enum';

export class ItemDto {
  @IsNotEmpty()
  id: string;

  @IsNotEmpty()
  name: string;
  
  value: number;
  
  valueType: ValueType;
}

