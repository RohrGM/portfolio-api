# Build stage
FROM gradle:8.11.1 AS build
WORKDIR /usr/app
COPY . .
RUN gradle build --no-daemon

# Package stage

FROM openjdk:21-alpine
ENV JAR_NAME=portfolio-api-1.0.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=build $APP_HOME .

EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME