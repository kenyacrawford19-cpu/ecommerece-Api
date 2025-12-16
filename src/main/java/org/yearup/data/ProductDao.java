package org.yearup.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao
{
    @GetMapping("{categoryId}/products")
    List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String subCategory);
   public List<Product> getProductsById(int categoryId);
    Product getById(int productId);
    Product create(Product product);
    void update(int productId, Product product);
    void delete(int productId);

    List<Product> listByCategoryId(int categoryId);
}
