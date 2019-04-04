package com.bbva.cruce.CruceOfertas.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecursosController {

private static final Logger LOGGER = LogManager.getLogger(RecursosController.class);
	
	@RequestMapping(value = "/{folder}/{pagina}", method = RequestMethod.GET)
	public String navegacion(@PathVariable ("folder") String folder, @PathVariable("pagina") String pagina) {
		return folder + "/" + pagina;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		try{
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
			String formattedDate = dateFormat.format(date);		
			model.addAttribute("serverTime", formattedDate );	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "ofertasAprobadas/ofertasAprobadas";
	}	
}
