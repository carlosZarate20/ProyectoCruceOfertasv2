package com.bbva.cruce.CruceOfertas.model;

import java.io.Serializable;

public class OfertasAprobadasPlantilla implements Serializable{

	private static final long serialVersionUID = 1L;

	private String tipoDocumento;
	private String doi;	
	
	// Atributos de la tabla
		private int id_carga;
		private int id;
		private String tipoDocBase;
		private String codDocBase;
		private String codigoCentral;
		private double lineaVehicular;
		private String vehiculoCuota;
		private String verificacionLaboral;
		private String verificacionDomiciliaria;
		private double sueldo;
		private String marcaPH;
		private String tieneOtraOferta;
		private int docSinceros;
		private String procesoRiesgos;
		private double factordVehicular;
		private double auto2DA;
		private String plazoVeh48;
		private String plazoVeh60;
		private String tipoRiesgo;
		private String tipoCliente;
		private String flujoOperativo;
		
		public OfertasAprobadasPlantilla() {
		
		}

		public String getTipoDocumento() {
			return tipoDocumento;
		}

		public void setTipoDocumento(String tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}

		public String getDoi() {
			return doi;
		}

		public void setDoi(String doi) {
			this.doi = doi;
		}

		public int getId_carga() {
			return id_carga;
		}

		public void setId_carga(int id_carga) {
			this.id_carga = id_carga;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTipoDocBase() {
			return tipoDocBase;
		}

		public void setTipoDocBase(String tipoDocBase) {
			this.tipoDocBase = tipoDocBase;
		}

		public String getCodDocBase() {
			return codDocBase;
		}

		public void setCodDocBase(String codDocBase) {
			this.codDocBase = codDocBase;
		}

		public String getCodigoCentral() {
			return codigoCentral;
		}

		public void setCodigoCentral(String codigoCentral) {
			this.codigoCentral = codigoCentral;
		}

		public double getLineaVehicular() {
			return lineaVehicular;
		}

		public void setLineaVehicular(double lineaVehicular) {
			this.lineaVehicular = lineaVehicular;
		}

		public String getVehiculoCuota() {
			return vehiculoCuota;
		}

		public void setVehiculoCuota(String vehiculoCuota) {
			this.vehiculoCuota = vehiculoCuota;
		}

		public String getVerificacionLaboral() {
			return verificacionLaboral;
		}

		public void setVerificacionLaboral(String verificacionLaboral) {
			this.verificacionLaboral = verificacionLaboral;
		}

		public String getVerificacionDomiciliaria() {
			return verificacionDomiciliaria;
		}

		public void setVerificacionDomiciliaria(String verificacionDomiciliaria) {
			this.verificacionDomiciliaria = verificacionDomiciliaria;
		}

		public double getSueldo() {
			return sueldo;
		}

		public void setSueldo(double sueldo) {
			this.sueldo = sueldo;
		}

		public String getMarcaPH() {
			return marcaPH;
		}

		public void setMarcaPH(String marcaPH) {
			this.marcaPH = marcaPH;
		}

		public String getTieneOtraOferta() {
			return tieneOtraOferta;
		}

		public void setTieneOtraOferta(String tieneOtraOferta) {
			this.tieneOtraOferta = tieneOtraOferta;
		}

		public int getDocSinceros() {
			return docSinceros;
		}

		public void setDocSinceros(int docSinceros) {
			this.docSinceros = docSinceros;
		}

		public String getProcesoRiesgos() {
			return procesoRiesgos;
		}

		public void setProcesoRiesgos(String procesoRiesgos) {
			this.procesoRiesgos = procesoRiesgos;
		}

		public double getFactordVehicular() {
			return factordVehicular;
		}

		public void setFactordVehicular(double factordVehicular) {
			this.factordVehicular = factordVehicular;
		}

		public double getAuto2DA() {
			return auto2DA;
		}

		public void setAuto2DA(double auto2da) {
			auto2DA = auto2da;
		}

		public String getPlazoVeh48() {
			return plazoVeh48;
		}

		public void setPlazoVeh48(String plazoVeh48) {
			this.plazoVeh48 = plazoVeh48;
		}

		public String getPlazoVeh60() {
			return plazoVeh60;
		}

		public void setPlazoVeh60(String plazoVeh60) {
			this.plazoVeh60 = plazoVeh60;
		}

		public String getTipoRiesgo() {
			return tipoRiesgo;
		}

		public void setTipoRiesgo(String tipoRiesgo) {
			this.tipoRiesgo = tipoRiesgo;
		}

		public String getTipoCliente() {
			return tipoCliente;
		}

		public void setTipoCliente(String tipoCliente) {
			this.tipoCliente = tipoCliente;
		}

		public String getFlujoOperativo() {
			return flujoOperativo;
		}

		public void setFlujoOperativo(String flujoOperativo) {
			this.flujoOperativo = flujoOperativo;
		}
		
}
