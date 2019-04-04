package com.bbva.cruce.CruceOfertas.dao;

import java.util.List;

import com.bbva.cruce.CruceOfertas.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceOfertas.model.CargaCabecera;
import com.bbva.cruce.CruceOfertas.model.OfertasAprobadasPlantilla;

public interface OfertasAprobadasDao {
	
	public Integer cargaCabeceraOferta(CargaCabecera cargaCabecera) throws Exception;
	
	public void cargaOfertas(List<AplicacionOfertasBean> listOfertasBean);
	
	public List<OfertasAprobadasPlantilla> cruceOferta(String tempIden, String identificador) throws Exception;
	
	public CargaCabecera getCargaCabecera(int identify);
	
	public void deleteOfertas(String identiOfertas) throws Exception;
}
