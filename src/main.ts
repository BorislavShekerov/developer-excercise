import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { getDbConnectionOptions, runDbMigrations } from './shared/utils';

async function bootstrap() {
  const app = await NestFactory.create(
    AppModule.forRoot(await getDbConnectionOptions(process.env.NODE_ENV)),
    );
  await app.listen(3000);
  await runDbMigrations();
}
bootstrap();
