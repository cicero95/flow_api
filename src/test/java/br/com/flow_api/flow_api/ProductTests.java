package br.com.flow_api.flow_api;

import br.com.flow_api.exceptions.ResourceNotFoundException;
import br.com.flow_api.exceptions.ServiceException;
import br.com.flow_api.product.Product;
import br.com.flow_api.product.ProductRepository;
import br.com.flow_api.product.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ProductTests {

    @Mock
    private ProductRepository productsRepository;

    @InjectMocks
    private ProductService productService;

    private static final Long PRODUCT_ID = 104L;
    private static final String ERROR_INSERT_PRODUCT = "Deve ser inserido ao menos um produto no estoque";
    private static final String ERROR_SAVE_PRODUCT = "Ocorreu um erro ao tentar salvar o produto.";


    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shoudNotReturnAllProducts() {
        Mockito.when(productsRepository.findAll(Mockito.any(Sort.class))).thenReturn(null);
        Assert.assertNull(productService.findAll());
    }

    @Test
    public void shouldReturnAllProducts() {
        List<Product> productsList = Arrays.asList(new Product());
        Mockito.when(productsRepository.findAll(Mockito.any(Sort.class))).thenReturn(productsList);
        Assert.assertNotNull(productsList);
        productService.findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void NotFoundIdExceptionProductFindById() {
        Product products = new Product();
        Mockito.when(productsRepository.findById(products.getId()))
                .thenThrow(new ResourceNotFoundException("Recurso não encontrado com o Id" + PRODUCT_ID));
        Assert.assertNotNull(productService.findById(PRODUCT_ID));
    }

    @Test(expected = ServiceException.class)
    public void productIdShoudBeNullOrZeroWhenInserting() throws ServiceException {
        Product products = new Product();
        products.setId(null);
        Mockito.when(productsRepository.save(Mockito.any())).thenReturn(ERROR_INSERT_PRODUCT);
        Assert.assertNull(productService.insert(products));
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowAnExceptionWhenInserting() throws ServiceException {
        Product products = new Product();
        Mockito.when(productsRepository.save(Mockito.any())).thenThrow(new MockitoException(ERROR_SAVE_PRODUCT));
        Assert.assertNull(productService.insert(products));
    }

    @Test
    public void productSaveSuccess() throws ServiceException {
        Product products = new Product();
        products.setId(1L);
        products.setProductCode(99999);
        products.setProductName("Arroz");
        products.setProductCategory("Alimento");
        products.setProductReceiptDate(LocalDateTime.now());
        products.setProductQuantity(30L);
        products.setMaxStock(100L);
        products.setMinStock(1L);
        Assert.assertNotEquals(products.getMinStock(), products.getMaxStock());
        Mockito.when(productsRepository.save(Mockito.any())).thenReturn(products);
        Assert.assertNotNull(productService.insert(products));
    }

    @Test
    public void shouldNotDeleteAProductWithoutId() {
        Product products = new Product();
        Mockito.when(productsRepository.findById(Mockito.anyLong())).thenReturn(null);
        productService.delete(products.getId());
    }

}
