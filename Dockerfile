
FROM amazoncorretto:24
LABEL authors="irfan"

WORKDIR /app
COPY build/libs/UserPool-0.0.1.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "UserPool-0.0.1.jar"]