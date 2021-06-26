package br.com.flow_api.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByid(long id);

    @Query(value = "SELECT * FROM tbl_client TC INNER JOIN tbl_client_physicalperson P ON TC.physicalperson_id = P.id WHERE P.cpf = ?",
            nativeQuery = true)
    Client existingCpf(String cpf);

    @Query(value = "SELECT * FROM tbl_client TC INNER JOIN tbl_client_legalperson L ON TC.legalperson_id = L.id WHERE L.cnpj = ?",
            nativeQuery = true)
    Client existingCnpj(String cnpj);
}
