import { InputDeal } from "../entity/inputdeal.entity";
import { InputDealDto } from "../dto/inputdeal.dto";
import { InputDealCreateDto } from "../dto/inputdeal.create.dto";

export const toInputDealDto = (inputDeals: InputDeal[]): InputDealDto[] => {
  let inputDealsDto: InputDealDto[] = inputDeals.map(inputDeal => {
    const inputDealDto = new InputDealDto();
    inputDealDto.id = inputDeal.id,
    inputDealDto.nameOfDeal = inputDeal.nameOfDeal
    
    return inputDealDto;
  });

  return inputDealsDto;
}

export const createDtoToInputDealEntity = (createInputDealDto: InputDealCreateDto): InputDeal => {
  const inputDeal = new InputDeal();
  inputDeal.nameOfDeal = createInputDealDto.nameOfDeal;
  
  return inputDeal;
}

export const dtoToInputDealEntity = (id: string, InputDealDto): InputDeal => {
  const inputDeal = new InputDeal();
  inputDeal.id = id;
  inputDeal.nameOfDeal = InputDealDto.nameOfDeal;
  
  return inputDeal;
}