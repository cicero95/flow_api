package br.com.flow_api.enums;

/**
 * 
 * @author Cicero Regis
 * @since 17/02/2021
 */

public enum PEOPLETYPE {

	CLIENTE("1", "Cliente"), 
	FUCIONARIO("2", "Funcionário"), 
	FORNECEDOR("3", "Fornecedor"), 
	CONTATO("4", "Contato");

	private String code;
	private String description;

	private PEOPLETYPE(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
