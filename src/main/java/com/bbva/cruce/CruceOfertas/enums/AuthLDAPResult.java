package com.bbva.cruce.CruceOfertas.enums;

public enum AuthLDAPResult {

	SUCCESSFUL("0000", "Autenticación satisfactoria"),
	INVALID_USER_OR_PASSWORD("0001", "Usuario o contraseña erronea"),
	USER_IS_NOT_IN_GROUP("0002", "Usuario ''{0}'' no encontrado en el grupo ''{1}'' con filtro base ''{2}''"),
	REQUIRED_FIELD("8000", "Campo ''{0}'' es requerido"),
	ACCESS_DENIED("9000", "La aplicación {0} no tiene acceso al servicio web"),
	GENERIC_ERROR("9001", ""),
	TIMEOUT_ERROR("9002", "Ocurrio un timeout");

	private String code;
	private String message;

	private AuthLDAPResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return message;
	}
}
