import { Entity, Column, ManyToOne } from 'typeorm';
import { BaseEntity } from '../../model/base.entity';
import { ValueType } from '../enum/value.type.enum';
import { InputDeal } from 'src/inputdeal/entity/inputdeal.entity';
import { Bill } from 'src/bill/entity/bill.entity';

@Entity({ name: 'item' })
export class Item extends BaseEntity {
  @Column({ type: 'varchar', length: 300 })
  name: string;

  @Column({ type: 'int'})
  value: number;

  @Column({
    type: 'enum',
    enum: ValueType,
  })
  valueType: ValueType;
  
  @ManyToOne(type => InputDeal, inputDeal => inputDeal.items)
  inputDeal: InputDeal;

  @ManyToOne(type => Bill, bill => bill.items)
  bill: Bill;
}