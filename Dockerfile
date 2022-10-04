FROM openjdk:8-jdk-alpine

WORKDIR /usr/src/app

RUN echo "COPY root GRADLE files ..."

COPY gradle/ ./gradle/
COPY nginx/conf.d/app.conf ./nginx/conf.d/app.conf
COPY gradlew ./
COPY gradlew.bat ./
COPY build.gradle ./
COPY settings.gradle ./

RUN echo "COPY sub-project GRADLE files..."

COPY rest-api/gradle/ ./rest-api/gradle/
COPY rest-api/gradlew ./rest-api/
COPY rest-api/gradlew.bat ./rest-api/
COPY rest-api/build.gradle ./rest-api/

RUN echo "COPY rest-api /src files..."

COPY rest-api/src/ ./rest-api/src/

RUN ./gradlew rest-api:bootJar

ENTRYPOINT ["java","-jar","rest-api/build/libs/qa-spring-boot-0.0.2.jar"] 