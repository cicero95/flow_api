package br.com.flow_api.client;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.flow_api.address.Address;
import br.com.flow_api.enums.GENDER;
import br.com.flow_api.enums.PEOPLETYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cicinho
 *
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_CLIENT")
public class Client implements Serializable {
    /**
     * Version 1.0
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Nome do cliente deve ser preenchido!")
    @Column(name = "NAME")
    private String name;

    @Column(name = "BIRTH")
    private String birth;

    @Column(name = "GENDER")
    private GENDER gender;

    @NotNull(message = "Tipo de cliente deve ser preenchido!")
    @Column(name = "PEOPLETYPE")
    private PEOPLETYPE peopleType;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELPHONE")
    private String telphone;

    @Column(name = "CELL")
    private String cell;

    @Column(name = "STATUS")
    private boolean status = true;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private ClientPhysicalperson physicalperson;

    @OneToOne(cascade = CascadeType.ALL)
    private ClientLegalperson legalperson;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @CreationTimestamp
    @Column(name = "CREATEDATE")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @UpdateTimestamp
    @Column(name = "UPDATEDATE")
    private LocalDateTime updated;

}
