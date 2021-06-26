package br.com.flow_api.constants;

public enum ErrorMessages {
	
	
	
	ERROR_NO_EXISTENT_SERVICE("Serviço inexistente");
	
	
	private final String error;

	ErrorMessages(String errorMessage) {
		error = errorMessage;
	}

	public String getErro() {
		return error;
	}
}
