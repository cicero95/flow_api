package br.com.flow_api.supplier;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.flow_api.address.Address;
import br.com.flow_api.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author cicinho
 * 
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_SUPPLIER")
public class Supplier implements Serializable {

	/**
	 * Version 1.0
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Nome do fornecedor deve ser preenchido!")
	@Column(name = "SUPPLIERNAME", nullable = false)
	private String supplierName;

	@NotNull(message = "CNPJ do fornecedor deve ser preenchido!")
	@Column(name = "SUPPLIERCNPJ", nullable = false)
	private String supplierCnpj;

	@NotNull(message = "Tamanho da compahia deve ser preenchido!")
	@Column(name = "COMPANYSIZE", nullable = false)
	private Long companySize;

	@NotNull(message = "Natureza legal do fornecedor deve ser preenchido!")
	@Column(name = "LEGALNATURE", nullable = false)
	private String legalNature;

	@OneToMany(cascade = CascadeType.ALL)
	@NotNull(message = "Endereço do fornecedor deve ser preenchido!")
	private List<Address> address = new ArrayList<>();

	@Column(name = "STATUS")
	private boolean status;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@CreationTimestamp
	@Column(name = "CREATEDATE")
	private LocalDateTime created;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@UpdateTimestamp
	@Column(name = "UPDATEDATE")
	private LocalDateTime updated;

}
