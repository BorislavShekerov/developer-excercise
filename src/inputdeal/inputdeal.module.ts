import { Module } from '@nestjs/common';
import { InputDealController } from './controller/inputdeal.controller';
import { InputDealService } from './service/inputdeal.service';
import { InputDeal } from './entity/inputdeal.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

@Module({
  imports: [
    TypeOrmModule.forFeature([InputDeal]),
  ],
  controllers: [InputDealController],
  providers: [InputDealService, InputDeal],
  exports: [InputDeal]
})
export class InputDealModule {}