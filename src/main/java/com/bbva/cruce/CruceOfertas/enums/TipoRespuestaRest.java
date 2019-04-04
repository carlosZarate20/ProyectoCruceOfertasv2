package com.bbva.cruce.CruceOfertas.enums;

public enum TipoRespuestaRest {

	EXITO(1), ERROR_VALIDACION(0), ERROR_GENERICO(-1);

	private Integer valor;

	private TipoRespuestaRest(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return this.valor;
	}
}
