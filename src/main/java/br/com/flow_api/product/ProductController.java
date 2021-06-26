package br.com.flow_api.product;

import br.com.flow_api.DTO.ProductsDTO;
import br.com.flow_api.exceptions.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
@Api(tags = {"Produtos"}, description = "Operações relacionadas a gerenciamento de produtos")
public class ProductController {

    private static final String SUCESSO = "sucesso";
    private static final String MENSAGEM = "mensagem";
    private static final String STATUS = "codigoRetorno";
    @Autowired
    ProductService productsService;

    @GetMapping(value = "/products")
    @ApiOperation(value = "Retorna uma lista de Produtos")
    public ResponseEntity<List<Product>> findlAll() {
        List<Product> products = productsService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/products/{id}")
    @ApiOperation(value = "Retorna o produto por id")
    public ResponseEntity<Object> findById(
            @ApiParam(value = "id", required = true) @Min(1) @RequestParam @Valid Long id) {
        HashMap<String, Object> response = new HashMap<>();
        Product products;
        try {
            products = productsService.findById(id);
        } catch (EntityNotFoundException e) {
            response.put(SUCESSO, false);
            response.put(MENSAGEM, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put(SUCESSO, false);
            response.put(MENSAGEM, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok().body(products);
    }

    @PostMapping(value = "/products")
    @ApiOperation(value = "Cadastra um novo produto", notes = "Cadastra um novo produto")
    public ResponseEntity<Object> insert(
            @ApiParam(name = "Produto", type = "String", required = true) @RequestBody Product products)
            throws ServiceException {
        HashMap<String, Object> response = new HashMap<>();
        try {
            products = productsService.insert(products);
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
        response.put(MENSAGEM, "Produto Cadastrado com sucesso!");
        response.put("Produto", products);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/products/{id}")
    @ApiOperation(value = "Deleta um produto por id")
    public ResponseEntity<Object> delete(@ApiParam(value = "id", required = true) @Min(1) @RequestParam @Valid Long id) {
        HashMap<String, Object> response = new HashMap<>();

        try {
            productsService.delete(id);
        } catch (EntityNotFoundException e) {
            response.put(SUCESSO, false);
            response.put(MENSAGEM, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put(SUCESSO, false);
            response.put(MENSAGEM, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put(SUCESSO, true);
        response.put(MENSAGEM, "Produto deletado com sucesso!");
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/products/{id}")
    @ApiOperation(value = "Atualiza produto por id")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product products) {
        products = productsService.update(id, products);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/productsFindByNegativebalance")
    @ApiOperation(value = "Retorna uma lista de produtos com saldo negativo")
    public ResponseEntity<Object> findByNegativebalance() throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        List<Product> products = new ArrayList<>();

        try {
            products = productsService.negativeBalance();
        } catch (ServiceException ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarRetorno(2, ex.getMessage()));
        } catch (EntityNotFoundException ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montarRetorno(1, ex.getMessage()));
        }

        response.put("Produto(s) econtrado(s)", products);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/productsFindByExpirationDate")
    @ApiOperation(value = "Retorna uma lista de produtos com validade proximo do vencimento")
    public ResponseEntity<Object> findByExpirationDate() throws ServiceException {
        HashMap<String, Object> response = new HashMap<>();
        List<Product> products = new ArrayList<>();
        try {
            products = productsService.findByExpirationDate();
        } catch (ServiceException ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarRetorno(2, ex.getMessage()));
        } catch (EntityNotFoundException ex) {
            log.error(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montarRetorno(1, ex.getMessage()));
        }
        List<ProductsDTO> productsDTOs = products.stream().map(p -> new ProductsDTO(p)).collect(Collectors.toList());
        response.put(SUCESSO, true);
        response.put("Produto(s) econtrado(s)", productsDTOs);
        return ResponseEntity.ok().body(productsDTOs);
    }

    private Map<String, Object> montarRetorno(Integer status, String mensagem) {
        Map<String, Object> retorno = new HashMap<>();
        retorno.put(STATUS, status);
        retorno.put(MENSAGEM, mensagem);
        return retorno;
    }
}
