import {
  Controller,
  Get,
  Param,
  Post,
  Body,
  Put,
  Delete,
  Req,
  HttpCode,
} from '@nestjs/common';
import { InputDealService } from "../service/inputdeal.service";
import { InputDealDto } from '../dto/inputdeal.dto';
import { InputDealCreateDto } from '../dto/inputdeal.create.dto';

@Controller('/input-deals')
export class InputDealController {
  constructor(private readonly inputDealService: InputDealService) {}

  @Get()
  async findAll(@Req() req: any): Promise<InputDealDto[]> {
    const inputDeals = await this.inputDealService.getAllInputDeals();
    return inputDeals;
  }

  @Get(':id')
  async findOne(@Param('id') id: string): Promise<InputDealDto> {
    return await this.inputDealService.getOneInputDeal(id);
  }

  @Post()
  async create(@Body() createInputDeal: InputDealCreateDto): Promise<InputDealDto> {
    return await this.inputDealService.createInputDeal(createInputDeal);
  }

  @Put(':id')
  async update(
    @Param('id') id: string,
    @Body() inputDeal: InputDealDto
  ): Promise<InputDealDto> {
    return await this.inputDealService.updateInputDeal(id, inputDeal);
  }

  @Delete(':id')
  @HttpCode(204)
  async delete(@Param('id') id: string): Promise<void> {
    await this.inputDealService.deleteInputDeal(id);
  }
}