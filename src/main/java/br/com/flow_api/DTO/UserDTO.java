package br.com.flow_api.DTO;

import br.com.flow_api.address.Address;
import br.com.flow_api.enums.GENDER;
import br.com.flow_api.role.Role;
import br.com.flow_api.user.User;
import br.com.flow_api.user.UserLegalperson;
import br.com.flow_api.user.UserPhysicalperson;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String name;

    private String lastName;

    private String email;

    private String birth;

    private String username;

    private String telphone;

    private String cell;

    private GENDER gender;

    private List<Address> address = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();

    private UserPhysicalperson physicalperson;

    private UserLegalperson legalperson;

    private boolean enabled;

    private boolean tokenexpired;

    public UserDTO(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.birth = user.getBirth();
        this.username = user.getUsername();
        this.telphone = user.getTelphone();
        this.cell = user.getCell();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.physicalperson = user.getPhysicalperson();
        this.legalperson = user.getLegalperson();
        this.enabled = user.isEnabled();
        this.tokenexpired = user.isTokenExpired();
        this.roles = user.getRoles();
    }
}
