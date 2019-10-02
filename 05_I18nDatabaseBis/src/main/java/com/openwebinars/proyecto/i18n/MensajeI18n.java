package com.openwebinars.proyecto.i18n;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Table(name="languages")
@Entity
public class MensajeI18n {
	
	@Id @GeneratedValue
	private long id;
	
	String locale;
	
	@Column(name="messagekey")
	String messageKey;
	
	@Column(name="messagevalue")
	String messageValue;

	
	public MensajeI18n(String locale, String messageKey, String messageValue) {
		this.locale = locale;
		this.messageKey = messageKey;
		this.messageValue = messageValue;
	}
	
	
	

}
