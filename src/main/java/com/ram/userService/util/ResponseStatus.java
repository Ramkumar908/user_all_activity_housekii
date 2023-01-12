package com.ram.userService.util;

public class ResponseStatus {
	
	
	private int responseCode;
	private String responseStatus;
	private String  StatusMessage;
	
	public ResponseStatus() {
		
	}
	public ResponseStatus(int responseCode, String responseStatus, String statusMessage) {
		super();
		this.responseCode = responseCode;
		this.responseStatus = responseStatus;
		StatusMessage = statusMessage;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}
	
	
	@Override
	public String toString() {
		return "ResponseStatus [responseCode=" + responseCode + ", responseStatus=" + responseStatus
				+ ", StatusMessage=" + StatusMessage + "]";
	}

}
