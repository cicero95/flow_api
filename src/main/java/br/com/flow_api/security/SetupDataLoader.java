package br.com.flow_api.security;

import br.com.flow_api.privilege.Privilege;
import br.com.flow_api.privilege.PrivilegeRepository;
import br.com.flow_api.role.Role;
import br.com.flow_api.role.RoleRepository;
import br.com.flow_api.user.User;
import br.com.flow_api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

        boolean alreadySetup = false;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PrivilegeRepository privilegeRepository;


        @Override
        @Transactional
        public void onApplicationEvent (ContextRefreshedEvent event){

            if (alreadySetup)
                return;
            Privilege readPrivilege
                    = createPrivilegeIfNotFound("READ_PRIVILEGE");
            Privilege writePrivilege
                    = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

            List<Privilege> adminPrivileges = Arrays.asList(
                    readPrivilege, writePrivilege);
            createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
            createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            User user = new User();
            user.setRoles(Arrays.asList(adminRole));
            alreadySetup = true;
        }

        @Transactional
        public Privilege createPrivilegeIfNotFound (String name){

            Privilege privilege = privilegeRepository.findByName(name);
            if (privilege == null) {
                privilege = new Privilege(name);
                privilegeRepository.save(privilege);
            }
            return privilege;
        }

        @Transactional
        Role createRoleIfNotFound (String name, List<Privilege> privileges){

            Role role = roleRepository.findByName(name);
            if (role == null) {
                role = new Role(name);
                role.setPrivileges(privileges);
                roleRepository.save(role);
            }
            return role;
        }
}
