FROM eclipse-temurin:25
LABEL authors="cvert"

WORKDIR /app

EXPOSE 9004

COPY package.jar /app/harmoGestionAPI.jar

ENTRYPOINT ["java", "-jar", "/app/harmoGestionAPI.jar"]
