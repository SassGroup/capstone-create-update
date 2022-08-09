FROM openjdk:11-jre-slim-buster

COPY target/idfc-loan-operation-0.1.jar /idfc-loan-operation-0.1.jar

EXPOSE 8081

ENTRYPOINT ["java","-Dmicronaut.environments=okteto","-jar","-Djdk.tls.client.protocols=TLSv1.2","-Dmicronaut.server.port=8081","/idfc-loan-operation-0.1.jar"]
