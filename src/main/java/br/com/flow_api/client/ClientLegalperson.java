package br.com.flow_api.client;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Cicero Regis
 * @since 17/02/2021
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_CLIENT_LEGALPERSON")
public class ClientLegalperson {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cnpj;
    private String fantasyName;
    private String razaoSocial;
}
