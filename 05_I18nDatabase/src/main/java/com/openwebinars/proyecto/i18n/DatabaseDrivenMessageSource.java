package com.openwebinars.proyecto.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;


@Component("messageSource")
public class DatabaseDrivenMessageSource extends AbstractMessageSource {
	
	private static final Logger log = LoggerFactory.getLogger(DatabaseDrivenMessageSource.class);
	
	@Autowired
	private MensajeI18nRepository repositorio;
	
	private static final String DEFAULT_LOCALE = "es";
	
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		MensajeI18n mensaje = repositorio.findFirstByMessageKeyAndLocale(code, locale.getLanguage());
		if (mensaje == null) {
			//Lo buscamos en el idioma por defecto
			mensaje = repositorio.findFirstByMessageKeyAndLocale(code, DEFAULT_LOCALE);
			//Si de nuevo es nulo
			if (mensaje == null) {
				log.debug(String.format("key: %s return null", code));
				return null;
			}
		}
		
		String valor = mensaje.getMessageValue();
		
		log.debug(String.format("key: %s -> value: %s", code, valor));
		
		return new MessageFormat(valor, locale);
		
	}

}
