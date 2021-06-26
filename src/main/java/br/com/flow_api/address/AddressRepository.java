package br.com.flow_api.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query(value = "SELECT  * FROM tbl_address tba  WHERE tba.companycep = ?1", nativeQuery = true)
	Optional<Address> findByCep(String companyCep);
}
