package com.bluepi.loan.base;

import com.aerospike.client.Host;

import io.micronaut.context.ApplicationContext;
// import com.idfc.dispatcher.impl.IOThreadDispatcher;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class IntegratorConstants {

    public static final ApplicationContext APPLICATION_CONTEXT_INSTANCE = ApplicationContext.run();
    public static final IntegratedConstantYml getFromYml = APPLICATION_CONTEXT_INSTANCE.getBean(IntegratedConstantYml.class);
    public static final  Host[] AEROSPIKE_HOST = getFromYml.aerospikeHostList();
    public static final String SERVICE_EXECUTOR = "service-executor";
    public static final String CORRELATION_ID = "CORRELATION_ID";
    public static final String REQUEST_HASH_ID = "REQUEST_HASH_ID";
    public static final String TOPIC = "topic";
    public static final String REQUEST_ID = "request_id";
    public static final String DEFERREDRESULT = "deferredResult";
    public static final String DATABASE_SERVICE = "databaseservice";
    public static final String APPLICATION_CONTEXT = "applicationContext";
    public static final String PRODUCER_SERVICE = "producerService";
    public static final String DELEGATOR = "delegator";
    public static final String DATA = "data";
   public static final String LAMS_PREFIX_FOR_ID = getFromYml.LAMS_PREFIX_FOR_ID;

    public static final String APPLICANT_ID = "applicantId";
    public static final String MESSAGE_OFFSET = "MESSAGE_OFFSET";
    public static final String COMPLETABLE_FUTURE = "COMPLETABLE_FUTURE";
    public static final String UNIQUE_IDENTIFICATION_ID = "uuid";
    public static final String APPLICATION_ID = "applicationId";
    public static final String AEROSPIKE_CLIENT = "AeroSpike_Client";
   public static final String AEROSPIKE_WRITE_SCHEMA = getFromYml.AEROSPIKE_COMMON_NAMESPACE;

    public static final String AEROSPIKE_RW_SCHEMA_LOAN_APPLICATION_TABLE = "Loan_application_data";
    public static final String AEROSPIKE_DUPLICATE_CHECK_TABLE = "Duplicate_check";
    public static final String AEROSPIKE_LOAN_APP_ID = "loanAppID";
    public static final String AEROSPIKE_APPLICANT_ID = "ApplicantId";
    public static final String AEROSPIKE_APPLICANT = "applicant";
    public static final String PAYLOAD_APPLICANT = "applicantInfo";
    public static final String AEROSPIKE_CO_APPLICANT = "coApplicant";
    public static final String PAYLOAD_CO_APPLICANT = "coApplicantInfo";
    public static final String AEROSPIKE_PROD_CON_KEY = "prodConKey";
    public static final String PAYLOAD_PROD_CON_KEY = "productConfigKey";
    public static final String PAYLOAD_LOAN_APP_SUMMARY = "loanApplicationInfo";
    public static final String AEROSPIKE_CREATED_TIME = "createdTime";
    public static final String AEROSPIKE_STAGE = "stage";
    public static final String PAYLOAD_STAGE = "stageInfo";
    public static final String AEROSPIKE_PROD_MASTER_PROD_CON_KEY = "prod_cfg_key";
    public static final String AEROSPIKE_WRITE_PRODUCT_MASTER = "product_metadata";
    public static final String AEROSPIKE_DUPLICATE_CHECK_BIN_NO = "mobile_no";
    public static final String AEROSPIKE_DUPLICATE_CHECK_BIN_APPLICATION_ID = "applicationId";


    public static final String AEROSPIKE_LOAN_APP_DATA = "loanAppData";

    public static final int IO_THREAD_CORE_POOL_SIZE = 10;
    public static final int IO_THREAD_MAX_POOL_SIZE = 20;
    public static final int IO_THREAD_MAX_INPUT_WAIT_QUEUE = 1;
    public static final int IO_THREAD_KEEP_ALIVE_TIME = 1000;
    public static final String CREATE_IO_THREAD_POOL_NAME = "create-service";
    public static final String UPDATE_IO_THREAD_POOL_NAME = "update-service";

    public static final String INTERNAL_UPDATE_KAFKA_TOPIC = getFromYml.INTERNAL_UPDATE_KAFKA_TOPIC;
    public static final ThreadPoolExecutor CREATE_IOD = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    public static final ThreadPoolExecutor UPDATE_IOD = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    // public static final IOThreadDispatcher<Map<String, Object>> CREATE_IOD = new IOThreadDispatcher<Map<String, Object>>(getFromYml.IOD_MIN_POOL_SIZE, getFromYml.IOD_MAX_POOL_SIZE, getFromYml.IOD_KEEP_ALIVE_TIME, CREATE_IO_THREAD_POOL_NAME, IO_THREAD_MAX_INPUT_WAIT_QUEUE);
    // public static final IOThreadDispatcher<Map<String, Object>> UPDATE_IOD = new IOThreadDispatcher<Map<String, Object>>(getFromYml.IOD_MIN_POOL_SIZE, getFromYml.IOD_MAX_POOL_SIZE, getFromYml.IOD_KEEP_ALIVE_TIME, UPDATE_IO_THREAD_POOL_NAME, IO_THREAD_MAX_INPUT_WAIT_QUEUE);
    public static final String KAFKA_SERVER_KEY = "bootstrap.servers";
    public static final String KAFKA_SERVER_VALUE = "localhost:9092";
    public static final String KAFKA_KEY_SERIALIZER_KEY = "key.serializer";
    public static final String KAFKA_KEY_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String KAFKA_VALUE_SERIALIZER_KEY = "value.serializer";
    public static final String KAFKA_VALUE_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    public static final String LOAN_APP_SEQ = "select application_id_seq.nextval from dual";
    public static final String APPLICANT_SEQ = "select applicant_id_seq.nextval from dual";

    // STATUS OF LOAN_REQUEST_STATUS_LOG TABLE
    public static final String STARTED = "STARTED";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String COMPLETED = "COMPLETED";
    public static final String ERROR = "ERROR";

    public static final String KAFKA_ACKS_KEY = "acks";
    public static final String KAFKA_ACKS_VALUE = "1";

//    public static final String MAX_POLL_RECORDS = "max.poll.records";
//    public static final int KAFKA_MAX_POLL_RECORDS = 20;

    public static boolean ORACLE_AUTO_COMMIT = true;
    public static int ORACLE_CONNECTION_TIMEOUT = 20000;

    public static String CACHE_PREPARED_STATEMENT = "cachePrepStmts";
    public static boolean CACHE_PREPARED_STATEMENT_VALUE = true;

    public static String PREPARED_STATEMENT_CACHE_SIZE = "prepStmtCacheSize";
    public static String PREPARED_STATEMENT_CACHE_SIZE_VALUE = "250";

    public static String PREPARED_STATEMENT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static String PREPARED_STATEMENT_CACHE_SQL_LIMIT_VALUE = "2048";


    //mongodb
    // public static String mongodb_url = "mongodb+srv://system:admin123@cluster1.nsjzv.mongodb.net/test";

    // public static String mongodb_application_data_database = "application_data";
    // public static String mongodb_duplicate_check_collection = "duplicate_check";
    // public static String mongodb_duplicate_check_mobile_no = "mobile_no";
    // public static String mongodb_duplicate_application_id = "application_id";

    public static String sequence_set = "sequence";
    public static String sequence_entity = "entity";
    public static String sequence_entity_application = "application";
    public static String sequence_entity_applicant = "applicant";
    public static String sequence_entity_counter = "counter";

    // public static String mongodb_applications_collection = "applications";
    // public static String mongodb_applications_application_id = "application_id";
    // public static String mongodb_applications_prod_config_key = "prod_config_key";
    // public static String mongodb_applications_data = "data";
    // public static String mongodb_applications_applicant_id = "applicant_id";

}
