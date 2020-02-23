import { Item } from "src/item/entity/item.entity";
import { ItemDto } from "src/item/dto/item.dto";
import { ItemCreateDto } from "src/item/dto/item.create.dto";

export const toItemDto = (items: Item[]): ItemDto[] => {
  let itemsDto: ItemDto[] = items.map(item => {
    const itemDto: ItemDto = {
      id: item.id,
      name: item.name,
      value: item.value,
      valueType: item.valueType
    }

    return itemDto;
  });

  return itemsDto;
}

export const createDtoToItemEntity = (createItemDto: ItemCreateDto): Item => {
  const item = new Item();
  item.name = createItemDto.name;
  item.value = createItemDto.value;
  item.valueType = createItemDto.valueType;

  return item;
}

export const dtoToItemEntity = (id: string, itemDto: ItemDto): Item => {
  const item = new Item();
  item.id = id
  item.name = itemDto.name;
  item.value = itemDto.value;
  item.valueType = itemDto.valueType;

  return item;
}
