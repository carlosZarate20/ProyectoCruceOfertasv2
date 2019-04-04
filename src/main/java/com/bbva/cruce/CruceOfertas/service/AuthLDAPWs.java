package com.bbva.cruce.CruceOfertas.service;

import java.util.Map;

import com.bbva.cruce.CruceOfertas.model.AutenticacionBean;
import com.bbva.cruce.CruceOfertas.model.AuthResponse;

public interface AuthLDAPWs {
	AuthResponse authenticate(AutenticacionBean autenticacionBean, Map<String, String> parameters) throws Exception;
}
