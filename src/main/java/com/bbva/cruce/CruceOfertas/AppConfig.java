package com.bbva.cruce.CruceOfertas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.bbva.cruce.CruceOfertas.util.Constantes;
import com.bbva.cruce.CruceOfertas.util.PropertyUtil;

@Configuration
public class AppConfig {
	
	public static final String ENTRY_CONFIG_PATH = "cruce.config.path";
	public static final String PROPERTIES_FILENAME = "cruce.properties";

	@Bean
	public PropertyUtil cruceProperties() throws IOException {
//		String configPath = System.getProperty(ENTRY_CONFIG_PATH);
		String configPath = Constantes.PROPERTIES;
//		return new PropertyUtil(configPath + File.separator + PROPERTIES_FILENAME);
		return new PropertyUtil(configPath);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return resolver;
	}
}
