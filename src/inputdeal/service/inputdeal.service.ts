import { Injectable, HttpException, HttpStatus } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { InputDeal } from "../entity/inputdeal.entity";
import { Repository } from "typeorm";
import { InputDealDto } from "../dto/inputdeal.dto";
import { toInputDealDto, createDtoToInputDealEntity, dtoToInputDealEntity } from "../mapper/inputdeal.mapper";
import { InputDealCreateDto } from "../dto/inputdeal.create.dto";

@Injectable()
export class InputDealService {
  constructor(
    @InjectRepository(InputDeal)
    private readonly inputDealRepo: Repository<InputDeal>
  ) {}

  async getAllInputDeals(): Promise<InputDealDto[]> {
    const inputDeals = await this.inputDealRepo.find();
    return toInputDealDto(inputDeals);
  }

  async getOneInputDeal(id: string): Promise<InputDealDto> {
    const inputDeal = await this.inputDealRepo.findOne({
      where : { id }
    });
    
    this.isInputDealExist(inputDeal);

    return toInputDealDto([inputDeal])[0]; 
  }

  async createInputDeal(createInputDeal: InputDealCreateDto): Promise<InputDealDto> {
    const inputDeal = createDtoToInputDealEntity(createInputDeal);

    await this.inputDealRepo.save(inputDeal);

    return toInputDealDto([inputDeal])[0]
  }

  async updateInputDeal(id: string, inputDealDto: InputDealDto): Promise<InputDealDto> {
    let inputDeal: InputDeal = await this.findInputDealById(id);

    this.isInputDealExist(inputDeal);

    inputDeal = dtoToInputDealEntity(id, inputDealDto);

    await this.inputDealRepo.update({ id }, inputDealDto);

    inputDeal = await this.findInputDealById(id);

    return toInputDealDto([inputDeal])[0];
  }

  async deleteInputDeal(id: string): Promise<void> {
    let inputDeal: InputDeal = await this.findInputDealById(id);

    this.isInputDealExist(inputDeal);

    await this.inputDealRepo.delete({ id });
  }

  private async findInputDealById(id: string): Promise<InputDeal> {
    return await this.inputDealRepo.findOne({ where: { id } });
  }

  private isInputDealExist(inputDeal: InputDeal) {
    if (!inputDeal) {
      throw new HttpException(`Input deal doesn't exist`, HttpStatus.BAD_REQUEST);
    }
  }
}