package br.com.flow_api.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	Supplier findById(long id);

}
