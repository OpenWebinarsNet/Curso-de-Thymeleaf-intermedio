package com.openwebinars.proyecto.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

@Component("messageSource")
public class DatabaseDrivenMessageSource extends AbstractMessageSource {
	

	@Autowired
	private MensajeI18nRepository repositorio;

	private static final String DEFAULT_LOCALE = "es";

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		MensajeI18n mensaje = repositorio.findFirstByMessageKeyAndLocale(code, locale.getLanguage());
		
		if (mensaje == null) {
			mensaje = repositorio.findFirstByMessageKeyAndLocale(code, DEFAULT_LOCALE);
			
			if (mensaje == null) {
				//logica para escribir en el log
				return null;
			}
			
		}
		
		String valor = mensaje.getMessageValue();
		
		
		return new MessageFormat(valor, locale);
		
	}
	
	

}
