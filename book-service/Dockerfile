FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY book-service/pom.xml ./book-service/pom.xml

RUN mvn -f book-service/pom.xml -DskipTests dependency:go-offline

COPY book-service/src ./book-service/src

RUN mvn -f book-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/book-service/target/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]
