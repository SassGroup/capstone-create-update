package com.bluepi.loan.processor;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.bluepi.loan.dao.LoanOperationDao;
import com.bluepi.loan.kafka.producer.IDFCProducer;
import com.bluepi.loan.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * create processor for create loan app id
 */
public class LoanProcessorCreate implements Runnable {

    private Logger log = LoggerFactory.getLogger(LoanProcessorCreate.class);

    private Map<String, Object> contextMap;

    /**
     * @return
     * @throws Exception
     */
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Map<String, Object> payload = (Map<String, Object>) contextMap.get(IntegratorConstants.DATA);
        CompletableFuture<Map<String, Object>> cf = (CompletableFuture<Map<String, Object>>) contextMap.get(IntegratorConstants.COMPLETABLE_FUTURE);
        String uuid = (String) contextMap.get(IntegratorConstants.UNIQUE_IDENTIFICATION_ID);
        MDC.put("uuid", uuid);
        log.info("Processing request inside call method for create loan request");
        try {
            log.info("create loan request before calling validation service in side call method ");
//            try {
//                ApplicationContext context = IntegratorConstants.APPLICATION_CONTEXT_INSTANCE;
//                DataValidator dataValidator = context.getBean(DataValidator.class);
//                log.info("context ref dataValidator ={} ", dataValidator);
//                ResponseModel rm = dataValidator.preparePayloadAndPerformValidation(payload);
//                if ("400".equals(rm.getStatusCode())) {
//                    cf.complete(getResults(rm));
//                    return null;
//                }
//
//            } catch (Exception e) {
//                log.error("Error in getting Application context for create loan app inside call method {}", e);
//                throw e;
//            }

            log.info("create loan request before calling Dao method in side call method with this uuid ");
            Map<String, Object> storeToCache = LoanOperationDao.getInstance().storeToCache(payload, uuid);
            if ("400".equals(storeToCache.get("statusCode"))) {
                cf.complete(storeToCache);
                return;
            }

            MessagePayload messagePayload;
            String applicationId = (String) ((Map) payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)).get("applicationId");
            if (payload.get(IntegratorConstants.PAYLOAD_CO_APPLICANT) == null) {
                messagePayload = new MessagePayload(applicationId, payload, uuid, "insertType");
            } else {
                messagePayload = new MessagePayload(applicationId, payload, uuid);
            }
            IDFCProducer.getInstance().sendMessage(applicationId, messagePayload, IntegratorConstants.INTERNAL_UPDATE_KAFKA_TOPIC);
            cf.complete(storeToCache);
            log.info("request thread release for create update loan inside create processor call method ");
        } catch (Exception e) {
            log.error("LoanProcessorCreate error {}", e);
            Map<String, Object> responseIncomplete = ResponseUtil.getResults("400", "loan creation failed." + e.getMessage(), uuid);
            cf.complete(responseIncomplete);
        }
        long end = System.currentTimeMillis();
        log.info("Total time in create request call() {}", (end - start));
        MDC.clear();
        return;
    }

    public String getName() {
        return "Loan IO Thread Dispatcher";
    }

    public void setContextMap(Map<String, Object> contextMap) {
        this.contextMap = contextMap;
    }

    /**
     * @param myBean
     * @return
     */
    private static Map<String, Object> getResults(Object myBean) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> props = m.convertValue(myBean, Map.class);
        return props;
    }


}
