FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY pom.xml pom.xml
COPY mvnw mvnw

RUN chmod +x mvnw && ./mvnw dependency:resolve -DskipTests
COPY src/ src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE "8080"

ENTRYPOINT ["java","-jar",  "app.jar"]