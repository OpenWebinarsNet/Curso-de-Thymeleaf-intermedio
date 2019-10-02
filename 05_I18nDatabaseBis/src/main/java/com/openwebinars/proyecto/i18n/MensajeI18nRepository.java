package com.openwebinars.proyecto.i18n;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeI18nRepository extends JpaRepository<MensajeI18n, Long>{

	MensajeI18n findFirstByMessageKeyAndLocale(String messageKey, String locale);
	
}
