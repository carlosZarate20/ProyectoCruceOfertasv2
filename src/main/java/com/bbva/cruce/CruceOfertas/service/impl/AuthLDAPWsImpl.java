package com.bbva.cruce.CruceOfertas.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbva.cruce.CruceOfertas.model.AutenticacionBean;
import com.bbva.cruce.CruceOfertas.model.AuthRequest;
import com.bbva.cruce.CruceOfertas.model.AuthResponse;
import com.bbva.cruce.CruceOfertas.service.AuthLDAPWs;
import com.google.gson.Gson;

@Service
public class AuthLDAPWsImpl implements AuthLDAPWs{

	private static final Logger LOGGER = LogManager.getLogger(AuthLDAPWsImpl.class);
	private static final Gson GSON = new Gson();
	
	@Override
	public AuthResponse authenticate(AutenticacionBean autenticacionBean, Map<String, String> parameters)
			throws Exception {
		long begin = System.nanoTime();
		LOGGER.info("*** INICIO LDAP Authentication ***");
		
		String applicationCode = parameters.get("ws.cruceOfertas.application.code");
		String urlString = parameters.get("ws.auth.ldap.service.url");
		
		DateFormat dfTransaccion = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String transactionId = dfTransaccion.format(new Date()) + applicationCode;
		
		LOGGER.info("transactionId: {}, username: {}", transactionId, autenticacionBean.getUsername());
		
		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		
		HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
		
		httpUrlConnection.setConnectTimeout(30_000);
		httpUrlConnection.setReadTimeout(30_000);
		httpUrlConnection.setDoInput(true);
		httpUrlConnection.setDoOutput(true);
		httpUrlConnection.setRequestProperty("Content-Type", "application/json");
		httpUrlConnection.setRequestProperty("Accept", "application/json");
		httpUrlConnection.setRequestProperty("applicationCode", applicationCode);
		httpUrlConnection.setRequestProperty("transactionId", transactionId);
		httpUrlConnection.setRequestMethod("GET");
		
		AuthRequest requestBean = new AuthRequest();
		requestBean.setBase(autenticacionBean.getBase());
		requestBean.setGroup(autenticacionBean.getGroup());
		requestBean.setUsername(autenticacionBean.getUsername());
		requestBean.setPassword(autenticacionBean.getPassword());
		String input = GSON.toJson(requestBean);
		
		OutputStream os = httpUrlConnection.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		
		if(httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + httpUrlConnection.getResponseCode());
		} else {
			LOGGER.debug("Success ----> " + httpUrlConnection.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), StandardCharsets.UTF_8));
		String outPut;
		StringBuilder sb = new StringBuilder();
		
		while((outPut = br.readLine()) != null) {
			LOGGER.debug(outPut);
			sb.append(outPut + '\n');
		}
		
		AuthResponse response = GSON.fromJson(sb.toString(), AuthResponse.class);
		
		httpUrlConnection.disconnect();
		
		LOGGER.debug("Response: {}", response);
		
		long end = System.nanoTime();
		long elapsedTime = TimeUnit.NANOSECONDS.toMillis(end - begin);
		LOGGER.info("*** FIN LDAP authentication ({} ms)***", elapsedTime);
		
		return response;
	}

}
