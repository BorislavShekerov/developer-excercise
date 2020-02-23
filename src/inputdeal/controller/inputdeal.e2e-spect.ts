import { INestApplication } from "@nestjs/common";
import { TestingModule, Test } from "@nestjs/testing";
import { InputDealModule } from "src/inputdeal/inputdeal.module";
import * as request from 'supertest';
import { AppModule } from "src/app.module";

describe('InputDealController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/input-deals (GET)', () => {
    return request(app.getHttpServer())
      .get('/')
      .expect(200);
  });
});