package com.bluepi.loan.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MessagePayload implements Serializable {


	private static final long serialVersionUID = 1L;
	private String applicationId;
	private Map<String, Object> payload;
	private String uuid;
	private String sourceType;
	private int retryCounter;
	private String exception;
	private List<String> kafkaList;
	private String destination =null;

	/**
	 *
	 * @param applicationId
	 * @param payload
	 * @param uuid
	 */
	public MessagePayload(String applicationId, Map<String, Object> payload, String uuid) {
		this.applicationId = applicationId;
		this.payload = payload;
		this.uuid = uuid;
		this.sourceType=null;
		this.retryCounter=0;
		this.exception=null;
		this.kafkaList=null;

	}

	public MessagePayload(String applicationId, Map<String, Object> payload, String uuid, String sourceType) {
		this.applicationId = applicationId;
		this.payload = payload;
		this.uuid = uuid;
		this.sourceType = sourceType;
		this.retryCounter=0;
		this.exception=null;
		this.kafkaList=null;
	}
	public MessagePayload(String applicationId, Map<String, Object> payload, String uuid, String sourceType,String exception,int retryCounter) {
		this.applicationId = applicationId;
		this.payload = payload;
		this.uuid = uuid;
		this.sourceType = sourceType;
		this.retryCounter=retryCounter;
		this.exception=exception;
		this.kafkaList=null;
	}

	public MessagePayload(String applicationId, Map<String, Object> payload, String uuid, String sourceType,List<String> kafkaList,String exception,int retryCounter) {
		this.applicationId = applicationId;
		this.payload = payload;
		this.uuid = uuid;
		this.sourceType = sourceType;
		this.retryCounter=retryCounter;
		this.exception=exception;
		this.kafkaList=kafkaList;
	}

	public MessagePayload(String applicationId, Map<String, Object> payload, String uuid, String sourceType, List<String> kafkaList) {
		this.applicationId = applicationId;
		this.payload = payload;
		this.uuid = uuid;
		this.sourceType = sourceType;
		this.kafkaList = kafkaList;
		this.retryCounter = 0;
		this.exception = null;
	}



	public void setException(String exception) {
		this.exception = exception;
	}



	public String getException() {
		return exception;
	}

	public List<String> getKafkaList() {
		return kafkaList;
	}
	public void setKafkaList(List<String> kafkaList) {
		this.kafkaList = kafkaList;
	}

	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	public MessagePayload() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	// private int retryCounter;
	public int getRetryCounter(){return retryCounter;}
	public void setRetryCounter(int retryCounter){this.retryCounter=retryCounter;}


}
