package br.com.flow_api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;


/**
 * @author Cicero Regis
 */


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USER_PHYSICALPERSON")
public class UserPhysicalperson {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CPF(message="cpf inválido")
    private String cpf;
    private String rg;
    private String emittingOrgan;
    private String nationality;

}
