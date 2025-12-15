FROM eclipse-temurin:17-jdk AS build
LABEL authors="sofijadjukic, sonjasukovic"

## build stage
WORKDIR /app
# get maven working, get pom.xml <- Spring dep, db dep etc
COPY mvnw pom.xml ./
# copy maven to /app
COPY .mvn .mvn
# download maven dependencies
RUN ./mvnw -DskipTests dependency:go-offline
# copy source code to /app
COPY src src
# build the jar
RUN ./mvnw -DskipTests package

## run stage
# jre - no compiler - smaller
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

