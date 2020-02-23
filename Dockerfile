FROM openjdk:8-jre-alpine
COPY build/libs/push-1.0.0.jar /push.jar
CMD ["/usr/bin/java", "-jar", "/push.jar"]
