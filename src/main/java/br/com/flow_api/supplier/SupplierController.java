package br.com.flow_api.supplier;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.flow_api.exceptions.ServiceException;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Fornecedores" }, description = "Operações relacionadas a gerenciamento de fornecedores")
public class SupplierController {

	private static final String SUCESSO = "sucesso";

	private static final String MENSAGEM = "mensagem";

	@Autowired
	SupplierService supplierService;

	@GetMapping("/suppliers")
	public ResponseEntity<List<Supplier>> findlAll() {
		List<Supplier> list = supplierService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/supplier/{id}")
	public ResponseEntity<Supplier> findById(@PathVariable(value = "id") long id) {
		Supplier obj = supplierService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/supplier")
	public ResponseEntity<Object> insert(@RequestBody Supplier fornecedor) throws ServiceException {
		HashMap<String, Object> response = new HashMap<>();
		try {
			fornecedor = supplierService.insert(fornecedor);
		} catch (EntityNotFoundException e) {
			response.put(SUCESSO, false);
			response.put(MENSAGEM, e.getMessage());
			return ResponseEntity.status((HttpStatus) HttpStatus.NOT_FOUND).body(response);
		} catch (ServiceException e) {
			response.put(SUCESSO, false);
			response.put(MENSAGEM, e.getMessage());
			return ResponseEntity.status((HttpStatus) HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		response.put(SUCESSO, true);
		response.put(MENSAGEM, "Fornecedor Cadastrado com sucesso!");
		response.put("empresa", fornecedor);
		return ResponseEntity.ok().body(response);
	}

	@PutMapping(value = "/supplier/{id}")
	public ResponseEntity<Supplier> update(@PathVariable Long id, @RequestBody Supplier obj) {
		obj = supplierService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
