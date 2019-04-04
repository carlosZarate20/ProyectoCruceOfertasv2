package com.bbva.cruce.CruceOfertas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbva.cruce.CruceOfertas.dao.OfertasAprobadasDao;
import com.bbva.cruce.CruceOfertas.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceOfertas.model.CargaCabecera;
import com.bbva.cruce.CruceOfertas.model.OfertasAprobadasPlantilla;
import com.bbva.cruce.CruceOfertas.model.RespuestaWS;
import com.bbva.cruce.CruceOfertas.service.OfertasAprobadasService;
import com.bbva.cruce.CruceOfertas.util.Constantes;

@Service
public class OfertasAprobadasServiceImpl implements OfertasAprobadasService{

	@Autowired
	private OfertasAprobadasDao ofertasAprobadasDao;
	
	@Override
	public List<OfertasAprobadasPlantilla> guardarArchivo(List<AplicacionOfertasBean> listOfertas, String identificador,
			String fileName, HttpServletRequest request, String userName) {
		RespuestaWS<List<OfertasAprobadasPlantilla>> respuesta = new RespuestaWS<>();
		List<OfertasAprobadasPlantilla> listOferts = new ArrayList<>();
		String tempIden = "TMP_" + identificador;
		int identity;
		
		try {
			CargaCabecera cargaCabecera = new CargaCabecera();
			cargaCabecera.setUsuario(userName);
			cargaCabecera.setNombreArchivo(fileName);
			cargaCabecera.setFechaCarga(identificador);
			
			identity = ofertasAprobadasDao.cargaCabeceraOferta(cargaCabecera);
			request.getSession().setAttribute("identity", identity);
			
			ofertasAprobadasDao.cargaOfertas(listOfertas);
			
			listOferts = ofertasAprobadasDao.cruceOferta(tempIden, identificador);
			request.getSession().setAttribute("listOferts", listOferts);
			
			
			respuesta.setEstado(Constantes.CODE_SUCCESSFUL);
			respuesta.setMensajeFuncional("Se registro en la tabla");
			respuesta.setObjetoRespuesta(listOferts);
			
		} catch(Exception e) {
			respuesta.setEstado(Constantes.CODE_WRONG);
			respuesta.setMensajeFuncional("Ocurrio un error en el registro");
			respuesta.setMensajeTecnico(e.getMessage());
			return listOferts;
		}
		return listOferts;
	}

	@Override
	public CargaCabecera getCargaUltima(HttpServletRequest request) {
		CargaCabecera cargaCabecera = new CargaCabecera();
		int identity;
		try {
			identity = (int) request.getSession().getAttribute("identity");
			
			cargaCabecera = ofertasAprobadasDao.getCargaCabecera(identity);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return cargaCabecera;
	}

	@Override
	public RespuestaWS<List<OfertasAprobadasPlantilla>> listaOfertasAprobadas(HttpServletRequest request) {
		RespuestaWS<List<OfertasAprobadasPlantilla>> respuesta = new RespuestaWS<>();
		try {	
			@SuppressWarnings("unchecked")
			List<OfertasAprobadasPlantilla> listOfertsExcel = (List<OfertasAprobadasPlantilla>) request.getSession().getAttribute("listOferts");
			respuesta.setEstado(Constantes.CODE_SUCCESSFUL);
			respuesta.setMensajeFuncional("Se registro en la tabla");
			respuesta.setObjetoRespuesta(listOfertsExcel);
			
		} catch(Exception e) {
			respuesta.setEstado(Constantes.CODE_WRONG);
			respuesta.setMensajeFuncional("Ocurrio un error en el registro");
			respuesta.setMensajeTecnico(e.getMessage());
			return respuesta;
		}

		return respuesta;
	}

	@Override
	public void deleteDataOfertas(String identOferta) {
		try {
			ofertasAprobadasDao.deleteOfertas(identOferta);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
