FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/micronaut-jpa-ms*.jar micronaut-jpa-ms.jar
CMD java ${JAVA_OPTS} -jar micronaut-jpa-ms.jar