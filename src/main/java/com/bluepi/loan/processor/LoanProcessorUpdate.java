package com.bluepi.loan.processor;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.bluepi.loan.model.ResponseModel;
import com.bluepi.loan.kafka.producer.IDFCProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * update processor for update loan app id
 */
@Singleton
public class LoanProcessorUpdate implements Runnable {

    private Logger log = LoggerFactory.getLogger(LoanProcessorUpdate.class);

    private Map<String, Object> contextMap;

    /**
     * @return
     */
    @Override
    public void run() {
        long start = System.currentTimeMillis();

        Map<String, Object> payload = (Map<String, Object>) contextMap.get(IntegratorConstants.DATA);
        CompletableFuture<Map<String, Object>> cf = (CompletableFuture<Map<String, Object>>) contextMap.get(IntegratorConstants.COMPLETABLE_FUTURE);
        String applicationId = (String) contextMap.get(IntegratorConstants.APPLICATION_ID);
        String uuid = (String) contextMap.get(IntegratorConstants.UNIQUE_IDENTIFICATION_ID);
        MDC.put(IntegratorConstants.APPLICATION_ID, applicationId);
        MDC.put(IntegratorConstants.UNIQUE_IDENTIFICATION_ID, uuid);
        String applicantId = null;
        log.info("Processing request inside call method for update loan request");
        try {
            log.info("update loan request before calling validation service in side call method ");
//            try {
//                ApplicationContext context = IntegratorConstants.APPLICATION_CONTEXT_INSTANCE;
//                DataValidator dataValidator = context.getBean(DataValidator.class);
//                log.info("context ref {} ", dataValidator);
//                ResponseModel rm = dataValidator.preparePayloadAndPerformValidation(payload);
//                if ("400".equals(rm.getStatusCode())) {
//                    cf.complete(getResults(rm));
//                    return null;
//                }
//
//            } catch (Exception e) {
//                log.error("Error in getting Application context for update loan app inside call method {}", e);
//                throw e;
//            }

            log.info("update loan request before calling internal update service in side call method  ");
            System.out.println(payload);
            applicantId = (String) ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).get(IntegratorConstants.APPLICANT_ID);
//            applicantId= applicantId.substring(applicantId.lastIndexOf('-')+1);
//            ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_APPLICANT)).put("applicantId",applicantId);
//            ((Map<String, Object>) payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)).put("applicationId",applicationId);
            MessagePayload messagePayload = new MessagePayload(applicationId, payload, uuid);
            log.info("message payload before sending update request to kafka :{}",messagePayload);
            IDFCProducer.getInstance().sendMessage(applicationId, messagePayload, IntegratorConstants.INTERNAL_UPDATE_KAFKA_TOPIC);
            log.info("request thread release for update  loan inside update processor call method ");
            ResponseModel rmSuccess = new ResponseModel("200", "Loan Application Updated successfully", applicationId, uuid, applicantId);
            cf.complete(getResults(rmSuccess));
        } catch (Exception e) {
            log.error("LoanProcessorUpdate error {}", e);
            ResponseModel rmFail = new ResponseModel("400", "Loan Application Update Failed", applicationId, uuid, applicantId);
            cf.complete(getResults(rmFail));
        }
        long end = System.currentTimeMillis();
        log.info("Total time in update request call() {}", (end - start));
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
