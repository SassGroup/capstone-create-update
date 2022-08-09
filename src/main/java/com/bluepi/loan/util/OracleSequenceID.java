package com.bluepi.loan.util;

import java.io.IOException;
import java.util.Map;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.config.AerospikeClientConfig;
import com.aerospike.client.Bin;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * calculating sequence number for application and applicant ID
 */
public class OracleSequenceID {

    private Logger log = LoggerFactory.getLogger(OracleSequenceID.class);

    private static volatile OracleSequenceID instance;

    // JdbcTemplate jdbcTemplate;

    // {
    //     ApplicationContext context = IntegratorConstants.APPLICATION_CONTEXT_INSTANCE;
    //     this.jdbcTemplate = context.getBean(JdbcTemplate.class);
    // }

    /**
     * private constructor
     */
    private OracleSequenceID() {

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    /**
     * @return singleton object of this class
     */
    public static OracleSequenceID getInstance() {

        if (instance == null) {
            synchronized (OracleSequenceID.class) {
                if (instance == null)
                    instance = new OracleSequenceID();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected OracleSequenceID readResolve() {
        return getInstance();
    }

    /**
     * @return sequence od ApplicantID
     * @throws IOException
     * @throws Exception
     */
    public synchronized String generateEntitySequence(String entity) throws IOException {
        
        // MongoCollection<Document> sequence_collection = MongoDB.getInstance().getClient().getDatabase(IntegratorConstants.mongodb_application_data_database).getCollection(IntegratorConstants.sequence_set);
        long start = System.currentTimeMillis();
        String value = null;
        try {
            AerospikeClient client = AerospikeClientConfig.getInstance().getAerospikeClientWrite();

            Key key = new Key(IntegratorConstants.getFromYml.AEROSPIKE_COMMON_NAMESPACE,IntegratorConstants.sequence_set, entity);

            Record r = client.get(null, key);

            if(r==null){
                client.put(null, key, new Bin[]{
                    new Bin(IntegratorConstants.sequence_entity,entity),
                    new Bin(IntegratorConstants.sequence_entity_counter,100l)
                });
                value = "100";
            }else{
                Long counter = (Long) r.bins.get(IntegratorConstants.sequence_entity_counter);
                counter+=1;
                value = String.valueOf(counter);
                client.put(null, key, new Bin[]{
                    new Bin(IntegratorConstants.sequence_entity,entity),
                    new Bin(IntegratorConstants.sequence_entity_counter,counter)
                });
            }

            long end = System.currentTimeMillis();
            log.info("Total time in ms while generating sequence for applicantId  {} ", (end - start));
        } catch (Exception e) {
            log.error("Error while  generating sequence for applicantId {}", e);
            throw e;
        }
        return value;
    }

}
