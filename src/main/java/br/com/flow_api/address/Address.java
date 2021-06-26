package br.com.flow_api.address;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ADDRESS")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Version 1.0
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "CEP", nullable = false)
	private String cep;

	@Column(name = "LOGRADOURO", nullable = false)
	private String logradouro;

	@Column(name = "COMPLEMENTO", nullable = false)
	private String complemento;

	@Column(name = "BAIRRO", nullable = false)
	private String bairro;

	@Column(name = "LOCALIDADE", nullable = false)
	private String localidade;

	@Column(name = "UF", nullable = false)
	private String uf;

}
