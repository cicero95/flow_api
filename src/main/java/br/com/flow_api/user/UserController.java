package br.com.flow_api.user;

import br.com.flow_api.DTO.ProductsDTO;
import br.com.flow_api.DTO.UserDTO;
import br.com.flow_api.exceptions.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.swing.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
@Api(tags = {"Usuários"}, description = "Operações relacionadas a gerenciamento de Usuários")
public class UserController {


    private static final String SUCESSO = "sucesso";
    private static final String MENSAGEM = "mensagem";
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "Retorna uma lista de Usuários")
    public ResponseEntity<List<UserDTO>> findlAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = users.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
        return ResponseEntity.ok().body(userDTOS);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Retorna o usuários por id")
    public ResponseEntity<UserDTO> findById(
            @ApiParam(value = "id", required = true) @Min(1) @RequestParam @Valid Long id) {
        User users = userService.findById(id);
        UserDTO userDTOS =  new UserDTO(users);
        return ResponseEntity.ok().body(userDTOS);
    }

    @PostMapping("/users")
    @ApiOperation(value = "Realiza o cadastro de Usuários")
    public ResponseEntity<Object> insert(@ApiParam(value = "Cadastro de Usuários", required = true) @Valid @RequestBody User users)
            throws ServiceException {
        HashMap<String, Object> response = new HashMap<>();


        try {
            users = userService.insert(users);
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
        response.put(MENSAGEM, "Usuário cadastrado com sucesso!");
        response.put("Usuários", users);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/user/{id}")
    @ApiOperation(value = "Atualiza usuário por id")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User users) {
        users = userService.update(id, users);
        return ResponseEntity.ok().body(users);
    }

}
