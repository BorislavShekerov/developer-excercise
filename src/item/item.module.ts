import { Module } from '@nestjs/common';
import { ItemController } from './controller/item.controller';
import { ItemService } from './service/item.service';
import { Item } from './entity/item.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

@Module({
  imports: [
    TypeOrmModule.forFeature([Item]),
  ],
  controllers: [ItemController],
  providers: [ItemService, Item],
  exports:[ ItemService, Item]
})
export class ItemModule {}