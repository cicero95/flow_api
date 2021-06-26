package br.com.flow_api.user;


import br.com.flow_api.address.Address;
import br.com.flow_api.enums.GENDER;
import br.com.flow_api.role.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USER")
public class User implements Serializable {
    /**
     * Version 1.0
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Campo nome deve ser preenchido!")
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull(message = "Campo sobrenome deve ser preenchido!")
    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Email(message = "Email inválido")
    @NotNull(message = "Campo E-mail deve ser preenchido!")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "BIRTH")
    private String birth;

    @NotNull(message = "Campo username deve ser preenchido!")
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotNull(message = "Campo senha deve ser preenchido!")
    @Size(min = 8, message = "A senha deve conter no mínino 8 caracteres")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "TELPHONE")
    private String telphone;

    @Column(name = "CELL")
    private String cell;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "TOKENEXPIRED")
    private boolean tokenExpired;

    @Column(name = "GENDER")
    private GENDER gender;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private UserPhysicalperson physicalperson;

    @OneToOne(cascade = CascadeType.ALL)
    private UserLegalperson legalperson;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @CreationTimestamp
    @Column(name = "CREATEDATE")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @UpdateTimestamp
    @Column(name = "UPDATEDATE")
    private LocalDateTime updated;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
