import { Module } from '@nestjs/common';
import { ItemController } from './controller/item.controller';
import { ItemService } from './service/item.service';
import { Item } from './entity/item.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { InputDeal } from 'src/inputdeal/entity/inputdeal.entity';
import { Bill } from 'src/bill/entity/bill.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Item, InputDeal, Bill]),
  ],
  controllers: [ItemController],
  providers: [ItemService, Item, InputDeal, Bill],
  exports:[ ItemService, Item]
})
export class ItemModule {}