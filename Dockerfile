FROM maven AS build

WORKDIR /usr/src/app

COPY . .

RUN mvn -Dmaven.test.skip=true package

RUN mv ./target/*.jar ./app.jar

FROM openjdk:17 AS runtime

ARG MEICASH_POSTGRES_HOST
ARG MEICASH_POSTGRES_PORT
ARG MEICASH_POSTGRES_DB
ARG MEICASH_POSTGRES_USER
ARG MEICASH_POSTGRES_PASSWORD
ARG MEICASH_SQL_INIT_MODE
WORKDIR /usr/src/app

COPY --from=build /usr/src/app/app.jar /usr/src/app/app.jar

CMD ["java", "-jar", "/usr/src/app/app.jar"]

EXPOSE 8080
