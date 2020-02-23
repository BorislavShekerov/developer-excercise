import { Injectable, HttpException, HttpStatus } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Item } from "../entity/item.entity";
import { Repository } from "typeorm";
import { ItemDto } from "../dto/item.dto";
import { toItemDto, createDtoToItemEntity, dtoToItemEntity } from "src/item/mapper/item.mapper";
import { ItemCreateDto } from "../dto/item.create.dto";

@Injectable()
export class ItemService {
  constructor(
    @InjectRepository(Item) 
    private readonly itemRepo: Repository<Item>
  ) {}

  async getAllItems(): Promise<ItemDto[]> {
    const items = await this.itemRepo.find();
    return toItemDto(items);
  }

  async getOneItem(id: string): Promise<ItemDto> {
    const item = await this.itemRepo.findOne({
      where : { id }
    });
    
    this.isItemExist(item);

    return toItemDto([item])[0];
  }

  async createItem(createItemDto: ItemCreateDto): Promise<ItemDto> {
    const item = createDtoToItemEntity(createItemDto);

    await this.itemRepo.save(item);

    return toItemDto([item])[0];
  }

  async updateItem(id: string, itemDto: ItemDto): Promise<ItemDto> {
    let item: Item = await this.findItemById(id);

    this.isItemExist(item);

    item = dtoToItemEntity(id, itemDto);

    await this.itemRepo.update({ id }, item);

    item = await this.findItemById(id);

    return toItemDto([item])[0];
  }

  async deleteItem(id: string): Promise<void> {
    const item: Item = await this.findItemById(id);

    this.isItemExist(item);

    await this.itemRepo.delete({ id });
  }

  private async findItemById(id: string): Promise<Item> {
    return await this.itemRepo.findOne({ where: { id } });
  }

  private isItemExist(item: Item) {
    if (!item) {
      throw new HttpException(`Item doesn't exist`, HttpStatus.BAD_REQUEST);
    }
  }
}