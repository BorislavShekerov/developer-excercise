FROM openjdk:11-jdk

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN ./gradlew build x -test

CMD ["java", "-jar", "build/libs/shoptill.jar"]
