mvn clean install 
java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -jar target/idfc-loan-operation-0.1.jar "-Dmicronaut.server.port=8082"




















