package com.bbva.cruce.CruceOfertas.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertyUtil {

	private Properties properties;

	public PropertyUtil(String propertyPath) throws IOException {
		this.load(propertyPath);
	}
	
	public String getString(String key) {
		return properties.containsKey(key) ? properties.getProperty(key) : key;
	}
	
	public void load(String propertyPath) throws IOException {
		Boolean existeArchivo = Files.exists(Paths.get(propertyPath));
		if (!existeArchivo) {
			throw new FileNotFoundException("Archivo no existe: " + propertyPath);
		}
		try (InputStream is = new FileInputStream(propertyPath)) {
			properties = new Properties();
			properties.load(is);
		}
	}
	
	public Properties filterByPrefix(String prefix) {
		Properties newProperties = new Properties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			if (entry.getKey().toString().startsWith(prefix)) {
				String newkey = entry.getKey().toString().substring(prefix.length());
				newProperties.put(newkey, entry.getValue().toString());
			}
		}
		return newProperties;
	}

	public Long getLong(String key) {
		if (!properties.containsKey(key)) {
			return null;
		}
		return ConvertUtil.convertToLong(getString(key));
	}

	public Integer getInteger(String key) {
		if (!properties.containsKey(key)) {
			return null;
		}
		return ConvertUtil.convertToInteger(getString(key));
	}

	public Double getDouble(String key) {
		if (!properties.containsKey(key)) {
			return null;
		}
		return ConvertUtil.convertToDouble(getString(key));
	}

	public Character getCharacter(String key) {
		if (!properties.containsKey(key)) {
			return null;
		}
		return ConvertUtil.convertToCharacter(getString(key));
	}
}
