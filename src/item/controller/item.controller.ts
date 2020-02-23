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
import { ItemService } from '../service/item.service';
import { ItemDto } from '../dto/item.dto';
import { ItemCreateDto } from '../dto/item.create.dto';


@Controller('/items')
export class ItemController {
  constructor(private readonly itemService: ItemService) {}

  @Get()
  async findAll(@Req() req: any): Promise<ItemDto[]> {
    const items = await this.itemService.getAllItems();
    return items;
  }

  @Get(':id')
  async findOne(@Param('id') id: string): Promise<ItemDto> {
    return await this.itemService.getOneItem(id);
  }

  @Post()
  async create(@Body() createItemDto: ItemCreateDto): Promise<ItemDto> {
    return await this.itemService.createItem(createItemDto);
  }

  @Put(':id')
  async update(
    @Param('id') id: string,
    @Body() itemDto: ItemDto
  ): Promise<ItemDto> {
    return await this.itemService.updateItem(id, itemDto);
  }

  @Delete(':id')
  @HttpCode(204)
  async delete(@Param('id') id: string): Promise<void> {
    await this.itemService.deleteItem(id);
  }
}