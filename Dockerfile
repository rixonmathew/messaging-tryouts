FROM openjdk:oraclelinux8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} messaging-tryouts.jar
ENTRYPOINT ["java","-jar","/messaging-tryouts.jar"]
EXPOSE 9898