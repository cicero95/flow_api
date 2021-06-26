package br.com.flow_api.enums;

/**
 * 
 * @author Cicero Regis
 * @since 17/02/2021
 */

public enum GENDER {

	MASCULINO("0", "Masculino"), FEMININO("1", "Feminino");

	private String code;
	private String description;

	GENDER(String code, String description) {
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
