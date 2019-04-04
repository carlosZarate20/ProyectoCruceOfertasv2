package com.bbva.cruce.CruceOfertas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bbva.cruce.CruceOfertas.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceOfertas.model.CargaCabecera;
import com.bbva.cruce.CruceOfertas.model.OfertasAprobadasPlantilla;
import com.bbva.cruce.CruceOfertas.model.RespuestaWS;

public interface OfertasAprobadasService {
	
	public List<OfertasAprobadasPlantilla> guardarArchivo(List<AplicacionOfertasBean> listOfertas, String identificador, String fileName, HttpServletRequest request,
			String userName);
	
	public CargaCabecera getCargaUltima(HttpServletRequest request);
	
	public RespuestaWS<List<OfertasAprobadasPlantilla>> listaOfertasAprobadas(HttpServletRequest  request);
	
	public void deleteDataOfertas(String identOferta);
}
