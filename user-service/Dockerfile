FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# cache-friendly: prvo samo POM-ovi
COPY pom.xml ./pom.xml
COPY user-service/pom.xml ./user-service/pom.xml

# deps
RUN mvn -f user-service/pom.xml -DskipTests dependency:go-offline

# sources
COPY user-service/src ./user-service/src

# build
RUN mvn -f user-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/user-service/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
