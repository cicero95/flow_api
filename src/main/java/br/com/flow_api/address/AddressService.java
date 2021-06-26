package br.com.flow_api.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Optional<Address> findAddressByCep(String cep) throws Exception {
		Optional<Address> address = Optional.ofNullable(ServiceCep.findByCep(cep));
		return address;
	}

}
