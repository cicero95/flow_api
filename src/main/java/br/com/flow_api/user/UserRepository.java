package br.com.flow_api.user;

import br.com.flow_api.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    @Query(value = "SELECT * FROM tbl_user TC INNER JOIN tbl_user_physicalperson P ON TC.physicalperson_id = P.id WHERE P.cpf = ?",
            nativeQuery = true)
    User existingCpf(String cpf);

    @Query(value = "SELECT * FROM tbl_user TC INNER JOIN tbl_user_legalperson L ON TC.legalperson_id = L.id WHERE L.cnpj = ?",
            nativeQuery = true)
    User existingCnpj(String cnpj);
}
