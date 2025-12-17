package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShoppingCartItem
{
    @JsonIgnore
    private int productId;

    private Product product;
    private int quantity;
    private BigDecimal discountPercent = BigDecimal.ZERO;

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product)
    {
        this.product = product;
        if (product != null)
            this.productId = product.getProductId();
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(BigDecimal discountPercent)
    {
        this.discountPercent = (discountPercent == null) ? BigDecimal.ZERO : discountPercent;
    }

    public BigDecimal getLineTotal()
    {
        if (product == null || product.getPrice() == null) return BigDecimal.ZERO;

        BigDecimal price = product.getPrice();
        BigDecimal qty = new BigDecimal(quantity);

        BigDecimal discountMultiplier =
                BigDecimal.ONE.subtract(discountPercent.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));

        return price.multiply(qty).multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
}


