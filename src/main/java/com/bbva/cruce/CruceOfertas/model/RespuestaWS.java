package com.bbva.cruce.CruceOfertas.model;

import java.io.Serializable;

public class RespuestaWS<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int estado;
	private String mensajeFuncional;
	private String mensajeTecnico;
	private T objetoRespuesta;
	
	public RespuestaWS() {
		
	}
	
	public RespuestaWS(T objetoRespuesta) {
		super();
		this.objetoRespuesta = objetoRespuesta;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getMensajeFuncional() {
		return mensajeFuncional;
	}

	public void setMensajeFuncional(String mensajeFuncional) {
		this.mensajeFuncional = mensajeFuncional;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

	public void setMensajeTecnico(String mensajeTecnico) {
		this.mensajeTecnico = mensajeTecnico;
	}

	public T getObjetoRespuesta() {
		return objetoRespuesta;
	}

	public void setObjetoRespuesta(T objetoRespuesta) {
		this.objetoRespuesta = objetoRespuesta;
	}
	
}
