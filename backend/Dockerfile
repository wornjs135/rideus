#FROM openjdk:8-jdk-alpine
#
#VOLUME /tmp
#COPY build/libs/beedly-0.0.1-SNAPSHOT.jar beedly.jar
#
#ENTRYPOINT ["java","-jar","beedly.jar"]

# wornjs135 도커 허브 계정명
# docker build -t wornjs135/beedly_spring .
# docker push wornjs135/beedly_spring
# docker run -d -p 8080:8080 --name beedly wornjs135/beedly_spring:latest

#FROM openjdk:8
#
#EXPOSE 8080
#ADD ./build/libs/rideus-0.0.1-SNAPSHOT.jar rideus.jar
#
#CMD ["java","-jar","beedly.jar"]


FROM openjdk:8 AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod =x ./gradlew
RUN ./gradlew bootJar
#RUN ./gradlew clean build --exclude-task test

FROM openjdk:8
COPY --from=builder build/libs/rideus-0.0.1-SNAPSHOT.jar rideus.jar

EXPOSE 8080
CMD ["java","-jar","rideus.jar"]