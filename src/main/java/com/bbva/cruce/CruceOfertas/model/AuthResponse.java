package com.bbva.cruce.CruceOfertas.model;

import java.io.Serializable;

public class AuthResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String resultCode;
	private String resultMessage;
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
