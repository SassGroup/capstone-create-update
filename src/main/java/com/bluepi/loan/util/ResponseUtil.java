package com.bluepi.loan.util;

import com.bluepi.loan.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ResponseUtil {

    /**
     * @param code
     * @param msg
     * @return
     */
    public static Map<String, Object> getResults(String code, String msg) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> props = m.convertValue(new ResponseModel(code, msg), Map.class);
        return props;
    }

    public static Map<String, Object> getResults(String code, String msg, String uuid) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> props = m.convertValue(new ResponseModel(code, msg, uuid), Map.class);
        return props;
    }

    /**
     * @param code
     * @param msg
     * @param applicationId
     * @return
     */
    public static Map<String, Object> getResults(String code, String msg, String applicationId, String uuid, String applicantId) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> props = m.convertValue(new ResponseModel(code, msg, applicationId, uuid, applicantId), Map.class);
        return props;
    }

    /**
     * @param code
     * @param msg
     * @param applicationId
     * @param uuid
     * @param applicantId
     * @param lams
     * @return
     */
    public static Map<String, Object> getResults(String code, String msg, String applicationId, String uuid, String applicantId, String lams) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Map<String, Object> props = m.convertValue(new ResponseModel(code, msg, applicationId, uuid, applicantId, lams), Map.class);
        return props;
    }

    public static Map<String, Object> duplicateResults(String code, String msg, String applicationId) {
        ObjectMapper m = new ObjectMapper();
        m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //Map<String, Object> props = m.convertValue(new ResponseModel(code, msg, applicationId,"hello"), Map.class);
        Map<String,Object> props = new HashMap<String, Object>();
        props.put("error code", code);
        props.put("message", msg);
        props.put("id with which it is active", applicationId);
        return props;
    }
}
