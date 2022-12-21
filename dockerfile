FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar", "${URL}", "${USERNAME}", "${PASSWORD}"]