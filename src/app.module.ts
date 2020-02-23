import { Module, DynamicModule } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ItemModule } from './item/item.module';
import { InputDealModule } from './inputdeal/inputdeal.module';
import { BillModule } from './bill/bill.module';
import { configService } from './config/config.service';
import { Connection, ConnectionOptions } from 'typeorm';

@Module({})
export class AppModule {
  static forRoot(connOptions: ConnectionOptions): DynamicModule {
    return {
      module: AppModule,
      controllers: [AppController],
      imports: [
        ItemModule,
        InputDealModule,
        BillModule,
        TypeOrmModule.forRoot(connOptions)
      ],
      providers: [AppService],
    };
  }
}
