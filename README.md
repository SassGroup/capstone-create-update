# MongoDB Implemetation
Create tables
>- `Database:- application_data, collection:- applications`

# Requirements

## Operating System

> Ubuntu - Tested
> Windows - Tested

## Environments

> Nodejs v16.14.2
> JDK 11.0.14
> kafka_2.13-3.0.0
> docker runtime engine

## IDE

> Intellij IDEA
> VsCode

# Setup

## Aerospike Setup

Run the below command to start aerospike docker container

```bash
sudo docker run --name aerospike-server -m 256MB -p 3000:3000 -p 3001:3001 -p 3002:3002 -p 3003:3003 -tid aerospike/aerospike-server
```

Run the below metadata Queries

```sql
# Aadhar Dummy service metadata

Insert into test.aadharTable(PK, aadharNo,emailID,address) values (1, "222222222222","abheekbabel@gmailcom","Bangalore");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (2, "333333333333","john@gmailcom","Mumbai");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (3, "444444444444","lara@gmailcom","Delhi");

Insert into test.aadharTable(PK, aadharNo,emailID,address) values (4, "398219351332","anjna@bluepi.in","Delhi");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (5, "836840018424","joseph@bluepi.in","Haryana");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (6, "389560164399","malika@bluepi.in","Uttar Pradesh");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (7, "389560164390","riya@gmail.com","Bihar");
Insert into test.aadharTable(PK, aadharNo,emailID,address) values (8, "999170341232","korukanti@bluepi.in","Hyderabad");

# Pan Dummy service metadata
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB) values (1,"BTNOX1045B","Tushar Thereja","Manoj Thereja","08-08-2000");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB) values (2,"AFMPW2946C","Rockey Kumar","Kunal Kumar","29-09-1995");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB) values (3,"CGLQV3837D","Riya Sharma","Rajat Sharma","14-03-1991");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB) values (4,"DHKRU4728E","Rachit Bisht","Karan Bisht","09-07-1989");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB) values (5,"EIJST5619F","Anand Kumar","Kamal Kumar","19-11-1997"):
CREATE INDEX panNumber ON test.pan_details (panNumber) STRING


Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (1,"BTNOX1045B","Tushar Thereja","Manoj Thereja","08-08-2000", 750, "Male");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (2,"AFMPW2946C","Rockey Kumar","Kunal Kumar","29-09-1995", 710, "Male");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (3,"CGLQV3837D","Riya Sharma","Rajat Sharma","14-03-1991", 660, "Female");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (4,"DHKRU4728E","Rachit Bisht","Karan Bisht","09-07-1989", 610, "Male");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (5,"EIJST5619F","Anand Kumar","Kamal Kumar","19-11-1997", 560, "Male");

Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (6,"QMCGJ3290P","Joseph Francis","Sidhant Francis","16-09-1983", 700, "Male");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (7,"UDHKS8456W","Anjna Bhati","Amit Bhati","10-09-1989", 750, "Female");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (8,"HWERT8955K","Mallika Bakshi","Daksh Bakshi","14-09-1992", 690, "Female");
Insert into test.pan_details(PK,panNumber,name,fatherName,DOB, cibilScore, gender) values (9,"JQBPS2422A","Shatrugna Rao Korukanti","Korukanti Madhusudan Rao","20-01-2000", 690, "Male");

CREATE INDEX panNumber ON test.pan_details (panNumber) STRING

# Swimlane Metadata queries

## swimlane_metadata table 1
Insert into test.swimlane_metadata_1(PK, cibil_score, cibil_type) values (1, 649, "Bronze");
Insert into test.swimlane_metadata_1(PK, cibil_score, cibil_type) values (2, 699, "Gold");
Insert into test.swimlane_metadata_1(PK, cibil_score, cibil_type) values (3, 749, "Titanium");
Insert into test.swimlane_metadata_1(PK, cibil_score, cibil_type) values (4, 750, "Platinum");
CREATE INDEX swimlane1Cibil_score ON test.swimlane_metadata_1 (cibil_score) NUMERIC;

## swimlane_metadata table 2
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (1, "Bronze", "Female", 1000000, 5, 11.25);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (2, "Gold", "Female", 1000000, 5, 11.30);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (3, "Titanium", "Female", 1000000, 5, 11.80);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (4, "Platinum", "Female", 1000000, 5, 12.30);

Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (5, "Bronze", "Male", 1000000, 5, 11.30);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (6, "Gold", "Male", 1000000, 5, 11.40);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (7, "Titanium", "Male", 1000000, 5, 11.90);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (8, "Platinum", "Male", 1000000, 5, 12.40);

Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (9, "Bronze", "Other", 1000000, 5, 11.30);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (10, "Gold", "Other", 1000000, 5, 11.40);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (11, "Titanium", "Other", 1000000, 5, 11.90);
Insert into test.swimlane_metadata_2(PK, cibil_type, gender, max_loan_amount, max_term, roi) values (12, "Platinum", "Other", 1000000, 5, 12.40);

# Index creation for read data
CREATE INDEX loanAppIDIndex ON test.Loan_application_data_r (loanAppID) STRING
```

## Oracle Setup

### Docker Oracle19c Image Setup

* Temporary Solution -
  Download the file from this drive link - [drive link](https://drive.google.com/drive/folders/1JjVqGzj7HExMiZTfU5Wnv9P3Mn1cBQoV?usp=sharing)
  Add the image to your docker image repository

```bash
docker load -i oracle19cDockerImage.tar    ----- for windows in cmd in 
adminitrator mode
sudo docker load < oracle19cDockerImage.tar   ------ for ubuntu
```

### Create the docker container

```bash
sudo docker  run --name oracle19c -m 2GB --network host -e ORACLE_PWD=admin123 
-e ORACLE_PDB=orclpdb1 -d oracle/database:19.3.0-ee
```

* Make sure the password remains same across all the services, currently the docker container credentials are
  Oraclecredential -

```bash
username : system
password :admin123
```

* To change the password of the oracle container, run the below command

```bash
sudo docker exec <container-name> ./setPassword.sh <new-password>
sudo docker exec oracle19c ./setPassword.sh admin123
```

* Check whether the container setup is done by inspecting the logs

```bash
sudo docker logs oracle19c
```

* Connect to the console after waiting for around 20 minutes.

```bash
sudo docker exec -ti <container-name> sqlplus system /<password>@orclpdb1  --- replace <container-name> and <password>
sudo docker exec -ti oracle19c sqlplus system/admin123@orclpdb1
```

### Table and Sequence Creation

```sql

drop table CoApplicant_Entity;
drop table applicant_entity;
drop table loan_application_entity;
drop table duplicatecheck;

CREATE TABLE Loan_Application_Entity (
   loanApplicationId NUMBER(30) PRIMARY KEY,
   stage CLOB DEFAULT NULL,
   product_key varchar(100) DEFAULT NULL,
   loanApplicationData CLOB DEFAULT NULL,
   createdTime TIMESTAMP DEFAULT NULL,
   updatedTime TIMESTAMP DEFAULT NULL
);

CREATE TABLE Applicant_Entity (
   loanApplicationId NUMBER(30),
   entity_id NUMBER(30) PRIMARY KEY,
   mobileNumber Long DEFAULT NULL,
   panNumber varchar(200) DEFAULT NULL,
   aadharNumber varchar(200) DEFAULT NULL,
   eligiblity_status varchar(200) DEFAULT NULL,
   applicant_data  CLOB DEFAULT NULL,
   createdTime TIMESTAMP DEFAULT NULL,
   updatedTime TIMESTAMP DEFAULT NULL,
   FOREIGN KEY (loanApplicationId) REFERENCES Loan_Application_Entity 
(loanApplicationId)
);

create table CoApplicant_Entity (
   entity_id NUMBER(30) PRIMARY KEY,
   applicant_id NUMBER(30),
   mobileNumber long DEFAULT NULL,
   panNumber varchar(200) DEFAULT NULL,
   aadharNumber varchar(200) DEFAULT NULL,
   eligiblity_status varchar(200) DEFAULT NULL,
   coApplicant_data CLOB DEFAULT NULL,
   createdTime TIMESTAMP DEFAULT NULL,
   updatedTime TIMESTAMP DEFAULT NULL,
   FOREIGN KEY (applicant_id) REFERENCES Applicant_Entity (entity_id)
);

CREATE TABLE LOAN_APPLICATION_AUDIT ("LOANAPPLICATIONID" NUMBER(30,0) 
NOT NULL ENABLE,"EVENTID" NUMBER(10,0),"EVENTOBJECT" CLOB DEFAULT NULL,"
CREATEDTIME" TIMESTAMP (6) DEFAULT NULL,"PRODUCT_KEY" VARCHAR2(100 
BYTE),"LOANAPPLICATIONDATA" CLOB,PRIMARY KEY ("EVENTID"));
CREATE TABLE LOAN_APPLICATION_INCREMENT ("LOANAPPLICATIONID" NUMBER
(30,0) NOT NULL ENABLE,"EVENTID" NUMBER(10,0) NOT NULL ENABLE,"
PRODUCT_KEY" VARCHAR2(100 BYTE) DEFAULT null, "LASTUPDATEDTIME" 
TIMESTAMP (6) DEFAULT null,"UPDATEDTIME" TIMESTAMP (6) DEFAULT null, 
"INCREMENTAL" CLOB DEFAULT null,FOREIGN KEY (EVENTID) REFERENCES 
loan_application_audit (EVENTID));

create table DuplicateCheck(
Business_Identifier varchar(20),
Application_ID NUMBER(30),
Applicant_ID NUMBER(30),
Mobile_Number NUMBER(11),
Status  NUMBER(2),
createdTime TIMESTAMP DEFAULT null,
updatedTime TIMESTAMP DEFAULT NULL,
constraint PK_D primary key (Business_Identifier, Mobile_Number,Status)
);

drop SEQUENCE application_id_seq;

drop SEQUENCE applicant_id_seq;

CREATE SEQUENCE application_id_seq
START WITH 10000000000000
INCREMENT BY 1;

CREATE SEQUENCE applicant_id_seq
START WITH 10000000000000
INCREMENT BY 1;

# Oracle Indexing

1. APPLICANT_ENTITY
DROP INDEX applicant_entity_PANNUMBER_idx;
create index applicant_entity_PANNUMBER_idx on applicant_entity
(PANNUMBER);
```

## Kafka Setup

Navigate to kafka_2.13-3.0.0 folder and start a terminal. Run the below commands to start the kafka zookeper and kafka server.

```bash
sudo bin/zookeeper-server-start.sh config/zookeeper.properties
sudo bin/kafka-server-start.sh config/server.properties
```

## Local Repository Setup

`https://bitbucket.org/bluepi-it/workspace/projects/digital_customer_onboarding_for_promotion`
Clone all the projects and run

```bash
mvn clean install -DskipTests # java projects
npm install  # node projects
```

| Repo | Command |
| --- | --- |
| [Adhaar_Validation](https://bitbucket.org/bluepi-it/adhaar_validation) | `java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -jar target/DummyAadhar-0.1.jar` |
| [Capstone_ETL](https://bitbucket.org/bluepi-it/capstone_etl) | `java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -Dmicronaut.environments=local -jar target/idfc-loan-etl-service-0.1.jar "-Dmicronaut.server.port=8095"` |
| [Common](https://bitbucket.org/bluepi-it/common) | This is not an independent service |
| [Create_Update](https://bitbucket.org/bluepi-it/create_update) | `java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -jar target/idfc-loan-operation-0.1.jar "-Dmicronaut.server.port=8082"` |
| [Email_OTP_One](https://bitbucket.org/bluepi-it/email_otp_one) | `npm run install` |
| [Fetch](https://bitbucket.org/bluepi-it/fetch) | `java -Xms1G -Xmx1G -XX:+UseG1GC -XX:+UseStringDeduplication -Dmicronaut.environments=local -jar target/idfc-loan-fetch-0.1.jar "-Dmicronaut.server.port=8083"` |
| [Front-end](https://bitbucket.org/bluepi-it/front-end) | `npm run install` |
| [Internal_Update](https://bitbucket.org/bluepi-it/internal_update) | `java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -Dmicronaut.environments=local -jar target/loan-application-internal-update-0.1.jar "-Dmicronaut.server.port=8085"` |
| [PAN_Validation](https://bitbucket.org/bluepi-it/pan_validation) | `java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -jar target/PanVerification-0.1.jar` |
| [Swimlane](https://bitbucket.org/bluepi-it/swimlane) | `java -Xms1G -Xmx1G -XX:+UseG1GC -XX:+UseStringDeduplication -jar target/swimlane-0.1.jar "-Dmicronaut.server.port=8093"` |
