package br.com.flow_api.address;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.flow_api.exceptions.ServiceException;
import io.swagger.annotations.Api;


/**
 * @author Cicero Regis
 */

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Endereços" }, description = "Operações relacionadas a pesquisa de endereço")
public class AddressController {

	private static final String SUCESSO = "sucesso";

	private static final String MENSAGEM = "mensagem";

	@Autowired
	private AddressService addressSerive;

	@GetMapping("/address")
	public ResponseEntity<List<Address>> findAll() {
		List<Address> list_address = addressSerive.findAll();
		return ResponseEntity.ok().body(list_address);

	}

	@GetMapping("/address/{cep}")
	public ResponseEntity<Object> findAddressByCep(@PathVariable(value = "cep") @RequestBody String cep) throws Exception {

		HashMap<String, Object> response = new HashMap<>();
		try {
			Optional<Address> address = addressSerive.findAddressByCep(cep);
			response.put(SUCESSO, true);
			response.put(MENSAGEM, "");
			response.put("Endereço", address);

		} catch (EntityNotFoundException e) {
			response.put(SUCESSO, false);
			response.put(MENSAGEM, e.getMessage());
			return ResponseEntity.status((HttpStatus) HttpStatus.NOT_FOUND).body(response);
		}

		return ResponseEntity.ok().body(response);
	}
}
