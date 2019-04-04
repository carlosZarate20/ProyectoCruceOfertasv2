package com.bbva.cruce.CruceOfertas.model;

public class CargaCabecera {
	private String usuario;
	private String nombreArchivo;
	private String fechaCarga;
	
	public CargaCabecera() {
		
	}

	public CargaCabecera(String usuario, String nombreArchivo, String fechaCarga) {
		super();
		this.usuario = usuario;
		this.nombreArchivo = nombreArchivo;
		this.fechaCarga = fechaCarga;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
}
