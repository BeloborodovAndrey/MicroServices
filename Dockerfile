
FROM openjdk:11

MAINTAINER Andrey

WORKDIR /app

COPY /scripts/wait /app/
RUN chmod +x /app

RUN apk --no-cache add curl

COPY /target/MS1-0.0.1-SNAPSHOT.jar /app/

CMD ["java","-jar","MS1.jar"]