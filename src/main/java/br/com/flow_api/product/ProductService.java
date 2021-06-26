package br.com.flow_api.product;

import br.com.flow_api.exceptions.DatabaseException;
import br.com.flow_api.exceptions.ResourceNotFoundException;
import br.com.flow_api.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Log4j2
@Service
public class ProductService {

    private static final String ERROR_SAVE_PRODUCT = "Ocorreu um erro ao tentar salvar o produto.";
    private static final String ERROR_INSERT_PRODUCT = "Deve ser inserido ao menos um produto no estoque";
    private static final String ERROR_MAX_STOCK = "Quantidade máxima excedida!!";
    private static final String ERROR_INSERT_MIN = "A quatidade minima deve ser menor que a quantidade cadastrada ";
    private static final String ERROR_GREATER_LESS = "Estoque minimo não pode ser maior ou igual ao estoque maximo!";
    private static final String ERROR_PRODUCTS_NOT_FOUND_NEGATIVE_BALANCE = "Não possui produto com saldo negativo em estoque!";
    private static final String ERROR_PRODUCTS_NOT_FOUND_TO_EXPIRATION_DATE = "Nenhum produto com vencido estoque!";
    private static final String ERROR_RETURN_PRODUCTS_DB = "Ocorreu um erro ao realizar a ação solicitada";

    @Autowired
    private ProductRepository productsRepository;
    private Random code = new Random();

    public List<Product> findAll() {
        return productsRepository.findAll(Sort.by(Sort.Direction.DESC, "productReceiptDate"));
    }

    public Product findById(Long id) {
        Optional<Product> obj = productsRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product obj) throws ServiceException {

        try {
            if (obj.getId() == null) {
                log.error(ERROR_INSERT_PRODUCT);
                throw new ServiceException(ERROR_INSERT_PRODUCT);
            }

            obj.setProductCode(this.code.nextInt(Integer.max(10000, 99999)));
            obj.setProductName(obj.getProductName().toUpperCase());
            obj.setProductCategory(obj.getProductCategory().toUpperCase());
            obj.setProductReceiptDate(obj.getProductReceiptDate());
            maxProductStock(obj);
            minProductStock(obj);
            minMaxProductStock(obj);

        } catch (Exception e) {
            log.error(ERROR_SAVE_PRODUCT);
            throw new ServiceException(ERROR_SAVE_PRODUCT);
        }

        return productsRepository.save(obj);
    }

    public void delete(Long id) {
        try {
            productsRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Product update(Long id, Product obj) {
        try {
            Product entity = productsRepository.getOne(id);
            updateData(entity, obj);
            return productsRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product obj) {
        entity.setProductName(obj.getProductName());
        entity.setProductQuantity(obj.getProductQuantity());
        entity.setProductPrice(obj.getProductPrice());
        entity.setProductDiscount(obj.getProductDiscount());
        entity.setStatus(obj.isStatus());
        entity.setSupplier(obj.getSupplier());
    }

    /**
     * @param maxstock end minStock
     * @return The storage capacity is the purchased product is checked before being
     * saved
     * @throws ServiceException
     */
    private Product maxProductStock(Product maxstock) throws ServiceException {
        Product products = new Product();
        if (maxstock.getProductQuantity() <= maxstock.getMaxStock()) {
            products.setProductQuantity(maxstock.getMaxStock());
        } else {
            throw new ServiceException(ERROR_MAX_STOCK);
        }
        return maxstock;

    }

    private Product minProductStock(Product minstock) throws ServiceException {

        if (minstock.getProductQuantity() < minstock.getMinStock()) {
            throw new ServiceException(
                    ERROR_INSERT_MIN + minstock.getProductQuantity() + " é menor que " + minstock.getMinStock());
        }
        return minstock;

    }

    private Product minMaxProductStock(Product minMaxStock) throws ServiceException {

        if (minMaxStock.getMinStock() >= minMaxStock.getMaxStock()) {
            throw new ServiceException(ERROR_GREATER_LESS);
        }

        return minMaxStock;
    }

    public List<Product> negativeBalance() throws ServiceException {

        List<Product> productsNegativeBalance = new ArrayList<>();
        try {

            productsNegativeBalance = productsRepository.findByNegativebalance();

        } catch (Exception e) {
            throw new ServiceException(ERROR_RETURN_PRODUCTS_DB);
        }

        if (productsNegativeBalance.size() <= 0) {
            log.warn(ERROR_PRODUCTS_NOT_FOUND_NEGATIVE_BALANCE);
        }

        return productsNegativeBalance;

    }

    public List<Product> findByExpirationDate() throws ServiceException {

        List<Product> productsExpirationDate = new ArrayList<>();
        try {
            productsExpirationDate = productsRepository.findByExpirationDate();
        } catch (Exception e) {
            log.error(ERROR_RETURN_PRODUCTS_DB);
        }
        if (productsExpirationDate.size() <= 0) {
            log.warn(ERROR_PRODUCTS_NOT_FOUND_TO_EXPIRATION_DATE);
        }
        return productsExpirationDate;
    }
}
