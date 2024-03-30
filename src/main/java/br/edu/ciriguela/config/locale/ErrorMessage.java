package br.edu.ciriguela.config.locale;

public enum ErrorMessage {

	INTERNAL_SERVER_ERROR("internal.server.generic.error"); 
	
	private final String messageKey;
	
	private ErrorMessage(String messageKey) {
	    this.messageKey = messageKey;
	}

	@Override
	public String toString() {
		return messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
