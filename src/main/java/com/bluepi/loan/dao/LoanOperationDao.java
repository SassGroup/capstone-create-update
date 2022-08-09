package com.bluepi.loan.dao;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.config.AerospikeClientConfig;
import com.bluepi.loan.config.AerospikeClientConfig;
import com.bluepi.loan.util.OracleSequenceID;
import com.bluepi.loan.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Write Only
 * --Loan Application Data , Loan Application Data
 * loanApplicationID  applicant(json) coapplicant(json) createdTime  updatedTime  stage(json)
 *
 * @author dell
 */
public class LoanOperationDao {

    // JdbcTemplate jdbcTemplate;

    // {
    //     ApplicationContext context = IntegratorConstants.APPLICATION_CONTEXT_INSTANCE;
    //     this.jdbcTemplate = context.getBean(JdbcTemplate.class);
    // }

    private static volatile LoanOperationDao instance;
    private Logger log = LoggerFactory.getLogger(LoanOperationDao.class);

    /**
     * private constructor
     */
    private LoanOperationDao() {

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    /**
     * @return singleton object of this class
     */
    public static LoanOperationDao getInstance() {

        if (instance == null) {
            synchronized (LoanOperationDao.class) {
                if (instance == null)
                    instance = new LoanOperationDao();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected LoanOperationDao readResolve() {
        return getInstance();
    }

    /**
     * @param payload
     * @return
     * @throws Exception
     */
    public Map<String, Object> storeToCache(Map<String, Object> payload, String uuid) {
        long start = System.currentTimeMillis();
        AerospikeClient client = null;
        log.info("inside storeToCache method into loanOperationDao for create loan request ");
        String applicationId = null;
        String applicantId = null;
        ObjectMapper objectMapper = new ObjectMapper();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        try {
            log.info("inside storeToCache before calling validateDuplicateCheck()");
            //String[] str = validateDuplicateCheck(payload);
            String flag = validateDuplicateCheck(payload);
            log.info("inside storeToCache after calling validateDuplicateCheck()");
            // if (str[0] != null && str[1] != null) {
            //     return ResponseUtil.getResults("400", "loanApplication already Active with this user", str[0], uuid, str[1]);
            // }
            if (flag != null) {
                 return ResponseUtil.duplicateResults("400", "loanApplication already Active with this user",flag);
            }
            log.info("inside storeTCache method for create loan request before getting connection from aerospike write schema");
            client = AerospikeClientConfig.getInstance().getAerospikeClientWrite();
            log.info("inside storeTCache method for create loan request after getting connection from aerospike write schema " + client);

            log.info("inside storeToCache before calling generateEntitySequence()");
            applicationId = OracleSequenceID.getInstance().generateEntitySequence(IntegratorConstants.sequence_entity_application);
            MDC.put(IntegratorConstants.APPLICATION_ID, applicationId);

            log.info("inside storeToCache before calling generateEntitySequence() for applicantId");
            applicantId = OracleSequenceID.getInstance().generateEntitySequence(IntegratorConstants.sequence_entity_applicant);

            if (applicationId != null && applicantId != null) {
                ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).put("applicantId", applicantId);
                ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)).put("applicationId", applicationId);

                log.info("inside storeToCache before calling insertInDuplicateCheck()");
                insertInDuplicateCheck(payload, applicationId);
                log.info("inside storeToCache after calling insertInDuplicateCheck()");

                Key key = new Key(IntegratorConstants.AEROSPIKE_WRITE_SCHEMA, IntegratorConstants.AEROSPIKE_RW_SCHEMA_LOAN_APPLICATION_TABLE, applicationId);
                Bin bin1 = new Bin(IntegratorConstants.AEROSPIKE_LOAN_APP_ID, applicationId);
                Bin bin2 = new Bin(IntegratorConstants.AEROSPIKE_PROD_CON_KEY, objectMapper.readValue(objectMapper.writeValueAsString(payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)), HashMap.class).get(IntegratorConstants.PAYLOAD_PROD_CON_KEY));
                Bin bin3 = new Bin(IntegratorConstants.AEROSPIKE_CREATED_TIME, " " + time + " ");
                Bin bin4 = new Bin(IntegratorConstants.AEROSPIKE_STAGE, objectMapper.writeValueAsString(payload.get(IntegratorConstants.PAYLOAD_STAGE)));
                Bin bin5 = new Bin(IntegratorConstants.AEROSPIKE_LOAN_APP_DATA, objectMapper.writeValueAsString(payload));
                Bin bin6 = new Bin(IntegratorConstants.AEROSPIKE_APPLICANT_ID, applicantId);
                client.put(null, key, bin1, bin2, bin3, bin4, bin5, bin6);
                log.info("inside storeToCache method data has been inserted successfully into aerospike write schema ");

                long end = System.currentTimeMillis();
                log.info("Total time in create request in storeToCache() {}", (end - start));
                return ResponseUtil.getResults("200", "loan created successfully", applicationId, uuid, applicantId);
            } else {
                return ResponseUtil.getResults("400", "loan creation failed because oracle is not generate sequence", applicationId, uuid, applicantId);
            }
        } catch (Exception e) {
            log.error("Error in inserting data into aerospike write schema {}", e);
            return ResponseUtil.getResults("400", "loan creation failed." + e.getMessage(), uuid);
        }
//        finally {
//            //  client.close();
//        }
    }

    private String validateDuplicateCheck(Map<String, Object> payload) throws Exception {
        String ls = null;
        AerospikeClient client = null;
        RecordSet r1 = null;
        Statement st = new Statement();
        QueryPolicy qp = new QueryPolicy();
        try{
            long start = System.currentTimeMillis();
            log.info("inside validateDuplicateCheck() in DAO class with payload: {}", payload);
            String mobileNumber = (String) ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).get("mobileNumber");
            st.setNamespace(IntegratorConstants.AEROSPIKE_WRITE_SCHEMA);
            st.setSetName(IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_TABLE);
            client = AerospikeClientConfig.getInstance().getAerospikeClientWrite();
            qp.filterExp = Exp.build(
                    Exp.eq(Exp.stringBin(IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_BIN_NO), Exp.val(mobileNumber)));
            r1 = client.query(qp, st);
            while (r1.next()) {
                Key key1 = r1.getKey();
                Record record = r1.getRecord();
                log.info("data fetched from duplicate check table {}",record);
                ls = ((String) record.bins.get(IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_BIN_APPLICATION_ID));
            }
            log.info("query executed successfully in validateDuplicateCheck() : {} ", ls);
            long end = System.currentTimeMillis();
            log.info("Total time in ms in validateDuplicateCheck()  {} ", (end - start));
        }
        catch (Exception e){
            log.error("Error in validateDuplicateCheck() {}", e);
            throw e;
        }
//        Key key = new Key(IntegratorConstants.AEROSPIKE_WRITE_SCHEMA, IntegratorConstants.AEROSPIKE_RW_SCHEMA_LOAN_APPLICATION_TABLE, applicationId);
//        try {
//            long start = System.currentTimeMillis();
//            log.info("inside validateDuplicateCheck() in DAO class with payload: {}", payload);
//            String mobileNumber = (String) ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).get("mobileNumber");
//            String query1 = "SELECT DuplicateCheck.Application_ID, Applicant_Entity.entity_id as applicantId" +
//                    " FROM DuplicateCheck " +
//                    " INNER JOIN Applicant_Entity" +
//                    " ON DuplicateCheck.Application_ID = Applicant_Entity.loanApplicationId where  DuplicateCheck.Mobile_Number = " + mobileNumber + " and DuplicateCheck.Status =0 ";
//            log.info(" query in validateDuplicateCheck() {} ", query1);
//
//            jdbcTemplate.query(query1, (rs) -> {
//                System.out.println(rs.getString(2));
//                ls[0] = (rs.getString(1));
//                ls[1] = (rs.getString(2));
//            });
//            log.info("query executed successfully in validateDuplicateCheck() : {} ", ls);
//            long end = System.currentTimeMillis();
//            log.info("Total time in ms in validateDuplicateCheck()  {} ", (end - start));
//        } catch (Exception e) {
//            log.error("Error in validateDuplicateCheck() {}", e);
//            throw e;
//        }

        return ls;
    }

    private void insertInDuplicateCheck(Map<String, Object> payload, String applicationId) throws IOException {
        try {
            long start = System.currentTimeMillis();
            AerospikeClient client = null;
            client = AerospikeClientConfig.getInstance().getAerospikeClientWrite();
            log.info("inside insertInDuplicateCheck() in DAO class with payload:{} , applicationId: {} ", payload, applicationId);
            String mobileNumber = (String) ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).get("mobileNumber");
            Key key = new Key(IntegratorConstants.AEROSPIKE_WRITE_SCHEMA, IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_TABLE, applicationId);
            Bin bin1 = new Bin(IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_BIN_APPLICATION_ID, applicationId);
            Bin bin2 = new Bin(IntegratorConstants.AEROSPIKE_DUPLICATE_CHECK_BIN_NO, mobileNumber);
            client.put(null, key, bin1, bin2);
//            String pck = (String) ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)).get(IntegratorConstants.PAYLOAD_PROD_CON_KEY);
//            String query3 = "INSERT INTO  DuplicateCheck (Business_Identifier,Application_ID,Mobile_Number,Status)  VALUES ('" + pck + "', " + applicationId + ", " + mobileNumber + ",0)";
            log.info(" Insert query in insertInDuplicateCheck()");
//            jdbcTemplate.execute(query3);
            log.info("executed successfully in insertInDuplicateCheck() ");
            long end = System.currentTimeMillis();
            log.info("Total time in ms in insertInDuplicateCheck()  {} ", (end - start));
        } catch (Exception e) {
            log.error("Error in insertInDuplicateCheck() {}", e);
            throw e;
        }

    }
}





