package br.com.flow_api.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.flow_api.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cicinho
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_PRODUCT")
public class Product implements Serializable {

    /**
     * Version 1.0
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Nome do produto deve ser preenchido!")
    @Column(name = "PRODUCTNAME")
    private String productName;

    @NotNull(message = "Quantidade do produto deve ser preenchido!")
    @Column(name = "PRODUCTQUANTITY")
    private Long productQuantity;

    @NotNull(message = "Alerta de saldo negativo deve ser preenchido!")
    @Column(name = "NEGATIVEBALANCE")
    private Long negativeBalance;

    @NotNull(message = "Preço do produto deve ser preenchido!")
    @Column(name = "PRODUCTPRICE")
    @Digits(integer = 3, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0,00")
    private BigDecimal productPrice;

    @Column(name = "PRODUCTCODE")
    private int productCode;

    @Column(name = "PRODUCTDISCOUNT")
    private Long productDiscount;

    @NotNull(message = "Quantidade mínima do produto em estoque deve ser informada!")
    @Column(name = "MINSTOCK")
    private Long minStock;

    @NotNull(message = "Quantidade maxima do produto em estoque deve ser informada!")
    @Column(name = "MAXSTOCK")
    private Long maxStock;

    @NotNull(message = "Preço de custo deverá ser informada!")
    @Column(name = "MANUFACTURINEXPENSES")
    @Digits(integer = 3, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0,00")
    private BigDecimal manufacturingExpenses;

    @Column(name = "PRODUCTPROFITPERCENT")
    private BigDecimal productProfitPercent;

    @Column(name = "PRODUCTPROFITREAIS")
    @Digits(integer = 3, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0,00")
    private BigDecimal productProfitReais;

    @NotNull(message = "Categoria do produto deverá ser informada!")
    @Column(name = "PRODUCTCATEGORY")
    private String productCategory;

    @NotNull(message = "Marca do produto deverá ser informada!")
    @Column(name = "PRODUCTCBRAND")
    private String productBrand;

    @NotNull(message = "Unidade de medida do produto deverá ser informada!")
    @Column(name = "UNITOFMEASUREMENT")
    private String unitOfMeasurement;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "BARCODE")
    private String BarCode;

    @ManyToOne
    private Supplier supplier;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "PRODUCTRECEPTDATE")
    private LocalDateTime productReceiptDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "EXPIRATIONDATE")
    private LocalDateTime expirationDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @CreationTimestamp
    @Column(name = "CREATEDATE")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @UpdateTimestamp
    @Column(name = "UPDATEDATE")
    private LocalDateTime updated;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

 
}
