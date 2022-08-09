package com.bluepi.loan.model;

public class ResponseModel {

	private String statusCode;
	private String message;
	private String applicationId;
	private String requestId;
	private String applicantId;
	
	//TODO: "message":"loan creation failed due to …… Validation message / Technical exception ", "applicationId":xxxxx

	private ResponseModel() {
	}

	public ResponseModel(String statusCode, String message, String applicationId, String uuid, String applicantId) {
		this.statusCode = statusCode;
		this.message = message;
		this.applicationId = applicationId;
		this.requestId = uuid ;
		this.applicantId = applicantId;
	}

	public ResponseModel(String statusCode, String message, String applicationId, String uuid, String applicantId,String lamsPrefix) {
		this.statusCode = statusCode;
		this.message = message;
		this.applicationId = lamsPrefix + applicationId;
		this.requestId = uuid ;
		this.applicantId = lamsPrefix + applicantId;
	}

	public ResponseModel(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public ResponseModel(String statusCode, String message, String uuid) {
		this.statusCode = statusCode;
		this.message = message;
		this.requestId = uuid;
	}



	public String getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getApplicantId() {
		return applicantId;
	}
}
