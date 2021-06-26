package br.com.flow_api.DTO;

import br.com.flow_api.product.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductsDTO {

	private String productName;

	private Long productQuantity;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime expirationDate;

	private Long negativeBalance;

	private int productCode;

	private String productCategory;

	private String productBrand;

	private boolean status;

	private String BarCode;

	public ProductsDTO(Product products) {

		this.productName = products.getProductName();
		this.productQuantity = products.getProductQuantity();
		this.expirationDate =  products.getExpirationDate();
		this.negativeBalance = products.getNegativeBalance();
		this.productCode = products.getProductCode();
		this.productCategory = products.getProductCategory();
		this.productBrand = products.getProductBrand();
		this.status = products.isStatus();
		this.BarCode = products.getBarCode();
	}

}
