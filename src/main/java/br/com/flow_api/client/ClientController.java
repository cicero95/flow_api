package br.com.flow_api.client;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.flow_api.exceptions.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
@Api(tags = { "Clientes" }, description = "Operações relacionadas a gerenciamento de clientes")
public class ClientController {

	@Autowired
	ClientService clientsService;

	private static final String SUCESSO = "sucesso";

	private static final String MENSAGEM = "mensagem";

	@GetMapping("/clients")
	@ApiOperation(value = "Retorna uma lista de Clientes")
	public ResponseEntity<List<Client>> findlAll() {
		List<Client> clients = clientsService.findAll();
		return ResponseEntity.ok().body(clients);
	}

	@GetMapping("/clients/{id}")
	@ApiOperation(value = "Retorna o cliente por id")
	public ResponseEntity<Client> findById(
			@ApiParam(value = "id", required = true) @Min(1) @RequestParam @Valid Long id) {
		Client clients = clientsService.findById(id);
		return ResponseEntity.ok().body(clients);
	}

	@PostMapping("/clients")
	@ApiOperation(value = "Realiza o cadastro de clientes")
	public ResponseEntity<Object> insert(@ApiParam(value = "Cadastro de clientes", required = true) @Valid @RequestBody Client clients)
			throws ServiceException {
		HashMap<String, Object> response = new HashMap<>();

		try {
			clients = clientsService.insert(clients);
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
		response.put(MENSAGEM, "Cliente cadastrado com sucesso!");
		response.put("Cliente", clients);
		return ResponseEntity.ok().body(response);
	}

	@PutMapping(value = "/clients/{id}")
	@ApiOperation(value = "Atualiza cliente por id")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client clients) {
		clients = clientsService.update(id, clients);
		return ResponseEntity.ok().body(clients);
	}
}
