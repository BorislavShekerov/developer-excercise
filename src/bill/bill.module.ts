import { Module } from '@nestjs/common';
import { BillController } from './controller/bill.controller';
import { BillService } from './service/bill.service';
import { Bill } from './entity/bill.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ItemModule } from '../item/item.module'
import { Item } from 'src/item/entity/item.entity';
import { InputDeal } from 'src/inputdeal/entity/inputdeal.entity';
import { InputDealModule } from 'src/inputdeal/inputdeal.module';

@Module({
  imports: [
    ItemModule,
    InputDealModule,
    TypeOrmModule.forFeature([Bill, Item, InputDeal]),
  ],
  controllers: [BillController],
  providers: [BillService],
})
export class BillModule {}