import { Entity, Column, OneToMany, ManyToOne } from 'typeorm';
import { BaseEntity } from '../../model/base.entity';
import { type } from 'os';
import { Item } from 'src/item/entity/item.entity';
import { Bill } from 'src/bill/entity/bill.entity';

@Entity({ name: 'inputdeal'})
export class InputDeal extends BaseEntity {
  @Column({ type: 'varchar', length: 50 })
  nameOfDeal: string;

  @OneToMany(type => Item, item => item.inputDeal)
  items: Item[];

  @ManyToOne(type => Bill, bill => bill.inputDeals)
  bill: Bill;
}