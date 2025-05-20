FROM maven:3.8.8-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/order-1.0.0.jar app.jar
#COPY /target/order-1.0.0.jar app.jar
EXPOSE 4003
CMD ["java", "-jar", "app.jar"]


# *** 21 version
#FROM amazoncorretto:21.0.4-alpine3.18
#WORKDIR /app
#COPY /target/order-1.0.0.jar app.jar
#EXPOSE 4003
#ENTRYPOINT ["java", "-jar", "app.jar"]