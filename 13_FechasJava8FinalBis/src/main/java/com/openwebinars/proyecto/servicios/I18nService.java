package com.openwebinars.proyecto.servicios;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class I18nService {
	
	private MessageSource messageSource;
	
	public I18nService(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
	public String getMessage(String label) {
		return messageSource.getMessage(label, null, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(String label, Object[] args) {
		return messageSource.getMessage(label, args, LocaleContextHolder.getLocale());
	}

}
