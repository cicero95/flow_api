package br.com.flow_api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 
 * @author Cicero Regis
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USER_LEGALPERSON")
public class UserLegalperson {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cnpj;
    private String fantasyName;
    private String razaoSocial;
}
