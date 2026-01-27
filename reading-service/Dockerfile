FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY reading-service/pom.xml ./reading-service/pom.xml

RUN mvn -f reading-service/pom.xml -DskipTests dependency:go-offline

COPY reading-service/src ./reading-service/src

RUN mvn -f reading-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/reading-service/target/reading-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]
