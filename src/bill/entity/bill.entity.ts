import { Entity, OneToMany, Column } from "typeorm";
import { Item } from "src/item/entity/item.entity";
import { InputDeal } from "src/inputdeal/entity/inputdeal.entity";
import { BaseEntity } from '../../model/base.entity';

@Entity({ name: 'bill' })
export class Bill extends BaseEntity {
  @OneToMany(type => Item, item => item.bill)
  items: Item[];

  @OneToMany(type => InputDeal, inputDeal => inputDeal.bill)
  inputDeals: InputDeal[];

  @Column({ type: 'varchar', length: 50 })
  totalBill: string;
}