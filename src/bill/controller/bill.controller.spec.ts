import { Test } from '@nestjs/testing';
import { TestingModule } from '@nestjs/testing/testing-module';
import { BillService } from '../service/bill.service';
import { BillController } from '../controller/bill.controller';
import { BillCreateDto } from '../dto/bill.create.dto';
import { ValueType } from 'src/item/enum/value.type.enum';
import { ItemModule } from 'src/item/item.module';
import { BillModule } from '../bill.module';
import { InputDealModule } from 'src/inputdeal/inputdeal.module';
import { Item } from 'src/item/entity/item.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

describe('BillController', () => {
  let testingModule: TestingModule;
  let controller: BillController;
  let spyService: BillService;

  beforeEach(async () => {
    testingModule = await Test.createTestingModule({
      controllers: [BillController],
      providers: [
        {
          provide: BillService,
          useFactory: () => ({
            findOne: jest.fn(() => true),
            findAll: jest.fn(() => true),
            create: jest.fn(() => true),
            delete: jest.fn(() => true),
          }),
        },
      ],
      imports: [
        TypeOrmModule.forFeature([Item])
        
      ]
    })
    .compile();
    controller = testingModule.get(BillController);
    spyService = testingModule.get(BillService);
  });

  describe('findOne', () => {
    it('should return bill by id', async () => {
      const uid = "uid";
      controller.findOne(uid);
      expect(spyService.getOneBill).toHaveBeenCalled();
    });
  });

  describe('findAll', () => {
    it('should return all bills', async () => {
      controller.findAll();
      expect(spyService.getAllBills).toHaveBeenCalled();
    });
  });

  describe('create', () => {
    it('should create bill', async () => {
      const bill = new BillCreateDto();
      bill.inputDeals = [
        {
          "nameOfDeal": "2 for 3",
          "items": [
            {
              "name": "apple",
              "value": 50,
              "valueType": ValueType.CLOUD
            },
            {
              "name": "banana",
              "value": 40,
              "valueType": ValueType.CLOUD
            },
            {
              "name": "tomato",
              "value": 30,
              "valueType": ValueType.CLOUD
            }
          ]
        }
      ];
      controller.create(bill);
      expect(spyService.createBill).toHaveBeenCalledWith(bill);
    });
  });

  describe('delete', () => {
    it('should delete bill by id', async () => {
      const uid = "uid";
      controller.delete(uid);
      expect(spyService.deleteBill).toHaveBeenCalled();
    });
  });

});