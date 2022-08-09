package com.bluepi.loan.util;

import com.bluepi.loan.base.IntegratorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PayloadValidator {

    private Logger log = LoggerFactory.getLogger(PayloadValidator.class);

    private static volatile PayloadValidator instance;

    /**
     * private constructor
     */
    private PayloadValidator() {

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    /**
     * @return singleton object of this class
     */
    public static PayloadValidator getInstance() {

        if (instance == null) {
            synchronized (PayloadValidator.class) {
                if (instance == null)
                    instance = new PayloadValidator();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected PayloadValidator readResolve() {
        return getInstance();
    }

    /**
     * @param applicationId
     * @param payload
     * @param uuid
     * @return
     */
    public Map<String, Object> validate(String applicationId, Map<String, Object> payload, String uuid) {
        try {
            long start = System.currentTimeMillis();
            log.info("enter in validate method");
            String applicationIdFromPayload = (String) ((Map) payload.get(IntegratorConstants.PAYLOAD_LOAN_APP_SUMMARY)).get("applicationId");
            log.info("application id from payload in validate method {}", applicationIdFromPayload);
            if (applicationIdFromPayload == null) {
                return ResponseUtil.getResults("400", "applicationId need to update into the payload", uuid);
            }
                if (!applicationIdFromPayload.equals(applicationId)) {
                    log.info("inside if condition in validate method ");
                    return ResponseUtil.getResults("400", "given applicationId " + applicationId + " is not match with payload applicationId", uuid);
                }

            long end = System.currentTimeMillis();
            log.info("Total time in for validate applicationId in PayloadValidator() {}", (end - start));

        } catch (Exception e) {
            log.error("Error while validating coApplicantInfo {}", e);
            return ResponseUtil.getResults("400", "error while validating applicationId from payload ", uuid);
        }
        return null;

    }

    /**
     * @param payload
     * @param uuid
     * @return
     */
    public Map<String, Object> validatePayload(Map<String, Object> payload, String uuid) {
        try {
            long start = System.currentTimeMillis();
            log.info("enter in validate method");
            if (payload.get(IntegratorConstants.PAYLOAD_CO_APPLICANT) != null) {
                log.info("inside if condition in validatePayload  method ");

                return ResponseUtil.getResults("400", "invalid payload for create loan application . we can't accept coApplicantInfo at time of loan creation", uuid);
            }
            long end = System.currentTimeMillis();
            log.info("Total time in for validate coApplicantInfo in PayloadValidator() {}", (end - start));
        } catch (Exception e) {
            log.error("Error while validating coApplicantInfo {}", e);
            return ResponseUtil.getResults("400", "error while validating coApplicantInfo ", uuid);
        }
        return null;

    }
}
