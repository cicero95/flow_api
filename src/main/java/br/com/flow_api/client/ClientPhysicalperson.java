package br.com.flow_api.client;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


/**
 * @author Cicero Regis
 * @since 17/02/2021
 */


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_CLIENT_PHYSICALPERSON")
public class ClientPhysicalperson {

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
