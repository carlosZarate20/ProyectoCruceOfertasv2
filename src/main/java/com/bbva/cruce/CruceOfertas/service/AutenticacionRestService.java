package com.bbva.cruce.CruceOfertas.service;

import com.bbva.cruce.CruceOfertas.model.AutenticacionBean;
import com.bbva.cruce.CruceOfertas.model.RespuestaWS;
import com.bbva.cruce.CruceOfertas.model.UsuarioAuthBean;

public interface AutenticacionRestService {
	RespuestaWS<UsuarioAuthBean> authenticate(AutenticacionBean autenticacionBean) throws Exception;
}
