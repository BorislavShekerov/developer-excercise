import {
  Controller,
  Get,
  Param,
  Post,
  Body,
  Delete,
  Req,
  HttpCode,
} from '@nestjs/common';
import { BillService } from '../service/bill.service';
import { BillDto } from '../dto/bill.dto';
import { BillCreateDto } from '../dto/bill.create.dto';

@Controller('/bills')
export class BillController {
  constructor(private readonly billService: BillService) {}

  @Get()
  async findAll(@Req() req: any): Promise<BillDto[]> {
    const inputDeals = await this.billService.getAllBills();
    return inputDeals;
  }

  @Get(':id')
  async findOne(@Param('id') id: string): Promise<BillDto> {
    return await this.billService.getOneBill(id);
  }

  @Post()
  async create(@Body() createBill: BillCreateDto): Promise<BillDto> {
    return await this.billService.createBill(createBill);
  }

  @Delete(':id')
  @HttpCode(204)
  async delete(@Param('id') id: string): Promise<void> {
    await this.billService.deleteBill(id);
  }
}