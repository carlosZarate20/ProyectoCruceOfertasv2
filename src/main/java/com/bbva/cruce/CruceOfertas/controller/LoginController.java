package com.bbva.cruce.CruceOfertas.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbva.cruce.CruceOfertas.enums.TipoRespuestaRest;
import com.bbva.cruce.CruceOfertas.model.AutenticacionBean;
import com.bbva.cruce.CruceOfertas.model.LoginResponse;
import com.bbva.cruce.CruceOfertas.model.RespuestaWS;
import com.bbva.cruce.CruceOfertas.model.UsuarioAuthBean;
import com.bbva.cruce.CruceOfertas.service.AutenticacionRestService;
import com.bbva.cruce.CruceOfertas.util.Constantes;
import com.bbva.cruce.CruceOfertas.util.PropertyUtil;

@RestController
public class LoginController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private AutenticacionRestService autenticacionRestService;
	
	@Autowired
	private PropertyUtil prop;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF_8_VALUE)
	@ResponseBody
	public LoginResponse login(@RequestBody AutenticacionBean autenticacionBean, HttpSession session, HttpServletRequest request) throws Exception {
		printHeaders(request);
		
		String ldapGroup = prop.getString("autentication.ldap.group");
		String ldapSearchBase = prop.getString("autentication.ldap.base");
		
		String ipAddress = request.getRemoteAddr();
		String forwardedForAddress = request.getHeader("X-Real-IP");
		LOGGER.debug("ipAddress: {}, forwardedFor: {}", ipAddress, forwardedForAddress);
		
		autenticacionBean.setGroup(ldapGroup);
		autenticacionBean.setBase(ldapSearchBase);
		autenticacionBean.setIpAddress(forwardedForAddress == null ? ipAddress : forwardedForAddress);
		
		RespuestaWS<UsuarioAuthBean> response = autenticacionRestService.authenticate(autenticacionBean);
		LoginResponse loginResponseBean = new LoginResponse();
		
		if (!TipoRespuestaRest.EXITO.getValor().equals(response.getEstado())) {
			session.invalidate();
			loginResponseBean.setSuccessful(false);
			loginResponseBean.setMessage(response.getMensajeFuncional());
			return loginResponseBean;
		}
		
		loginResponseBean.setSuccessful(true);
		loginResponseBean.setMessage("Autenticacion realizada con exito");
		session.setMaxInactiveInterval(900);
		session.setAttribute(Constantes.KEY_USERNAME, autenticacionBean.getUsername());
		
		LOGGER.info("Login Ok: {}", autenticacionBean.getUsername());
		
		return loginResponseBean;
	}
	
	private void printHeaders(HttpServletRequest request) {
		if (LOGGER.isTraceEnabled()) {
			Enumeration<?> headers = request.getHeaderNames();
			while (headers.hasMoreElements()) {
				String headerName = headers.nextElement().toString();
				String headerValue = request.getHeader(headerName);
				LOGGER.trace("{} = {}", headerName, headerValue);
			}
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
		session.setAttribute(Constantes.KEY_USERNAME, null);
		session.invalidate();
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/loginBBVA");
	}
}
