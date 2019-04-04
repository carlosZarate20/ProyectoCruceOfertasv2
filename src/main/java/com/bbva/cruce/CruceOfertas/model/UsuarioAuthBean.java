package com.bbva.cruce.CruceOfertas.model;

import java.io.Serializable;

public class UsuarioAuthBean implements Serializable{
private static final long serialVersionUID = 1L;
	
	private boolean authenticated;
	private String message;
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
