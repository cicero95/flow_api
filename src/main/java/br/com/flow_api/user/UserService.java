package br.com.flow_api.user;

import br.com.flow_api.DTO.UserDTO;
import br.com.flow_api.exceptions.ResourceNotFoundException;
import br.com.flow_api.exceptions.ServiceException;
import br.com.flow_api.external.EmailUtils;
import br.com.flow_api.external.Utils;
import br.com.flow_api.role.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserService {


    private static final String ERROR_INSERT_USER = "Não foi possivel cadastrar usuário";
    private static final String ERROR_USER_CPF_EXSITS = "Já existe usuário cadastrado com o CPF - %s ";
    private static final String ERROR_USER_CNPJ_EXSITS = "Já existe usuário cadastrado com o CNPJ - %s ";
    private static final String ERROR_SEARCH_USER = "Ocorreu um erro ao tentar buscar os dados do usuário";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailUtils emailUtils;

    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public User findById(Long id) {
        Optional<User> User = userRepository.findById(id);
        return User.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user) throws ServiceException {
        verifyCpfCnpj(user);
        User email = new User();

        email.setName(user.getName());
        email.setEmail(user.getEmail());
        email.setPassword(user.getPassword());
        email.setUsername(user.getUsername());
        user.setPassword(Utils.encryptPassword(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        try {

            userRepository.save(user);
            emailUtils.sendEmail(email);
        } catch (Exception e) {
            log.error(ERROR_INSERT_USER);
            throw new ServiceException(ERROR_INSERT_USER);
        }
        return user;
    }

    public User update(Long id, User obj) {
        try {
            User User = userRepository.getOne(id);
            updateData(User, obj);
            return userRepository.save(User);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User user, User obj) {

        user.setName(obj.getName());
        user.setBirth(obj.getBirth());
        user.setAddress(obj.getAddress());
        user.setLegalperson(obj.getLegalperson());
        user.setPhysicalperson(obj.getPhysicalperson());
    }

    private User verifyCpfCnpj(User user) throws ServiceException {
        User cpfUser = new User();
        User cnpjUser = new User();
        try {
            cpfUser = userRepository.existingCpf(user.getPhysicalperson().getCpf());
            cnpjUser = userRepository.existingCnpj(user.getLegalperson().getCnpj());

        } catch (Exception e) {
            throw new ServiceException(ERROR_SEARCH_USER);
        }

        if (cpfUser != null) {
            log.warn(ERROR_USER_CPF_EXSITS.replace("%s", cpfUser.getPhysicalperson().getCpf()));
            throw new EntityNotFoundException(ERROR_USER_CPF_EXSITS.replace("%s", cpfUser.getPhysicalperson().getCpf()));
        }

        if (cnpjUser != null) {
            log.warn(ERROR_USER_CNPJ_EXSITS.replace("%s", cpfUser.getLegalperson().getCnpj()));
            throw new EntityNotFoundException(ERROR_USER_CNPJ_EXSITS.replace("%s", cnpjUser.getLegalperson().getCnpj()));
        }

        if (user.getPhysicalperson().getCpf() == null || user.getPhysicalperson().getCpf().equals("")) {
            user.getPhysicalperson().setCpf("NÃO INFORMADO");

        } else if (user.getLegalperson().getCnpj() == null || user.getLegalperson().getCnpj().equals("")) {
            user.getLegalperson().setCnpj("NÃO INFORMADO");
        }
        return user;
    }
}
