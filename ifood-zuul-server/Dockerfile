# Alpine because it's lighter
FROM openjdk:8-jdk-alpine
MAINTAINER Isaias Tavares <isaiasengsoft@gmail.com>

# Set ENV variables
ENV PORT=9092

# Add JAR file and run it as entrypoint
ADD target/ifood-zuul-server.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expose the port
EXPOSE 9092