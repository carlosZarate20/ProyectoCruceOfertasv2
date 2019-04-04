package com.bbva.cruce.CruceOfertas.model;

import java.io.Serializable;

public class LoginResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean successful;
	private String message;
	
	public boolean isSuccessful() {
		return successful;
	}
	
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
