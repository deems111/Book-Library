FROM maven:latest

RUN mkdir -p /docker
WORKDIR /docker

ADD pom.xml /docker

RUN mvn clean -B
RUN mvn dependency:resolve-plugins -B
RUN mvn dependency:resolve -B

ADD ./ /docker/

RUN mvn install -B -DskipTests

FROM openjdk:11-jre-slim

RUN mkdir -p /docker
WORKDIR /docker
COPY --from=0 /docker/target/library.jar /docker/

EXPOSE 8080
CMD ["java", "-jar", "/docker/library.jar"]

