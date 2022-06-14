# Build phase
FROM eclipse-temurin:17.0.3_7-jdk-alpine as builder
RUN apk update
RUN apk add maven
WORKDIR /home/app
COPY . .
RUN mvn package -DskipTests -f pom.xml

# Image phase
FROM eclipse-temurin:17.0.3_7-jre-alpine

EXPOSE 8080/tcp
WORKDIR /home/app

# build target from Maven:
COPY --from=builder /home/app/target/CrypticRadio-1.0.jar app.jar

CMD ["java", "-jar", "./app.jar"]