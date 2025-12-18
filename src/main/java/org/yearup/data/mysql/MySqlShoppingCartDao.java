package org.yearup.data.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    private final JdbcTemplate jdbcTemplate;

    public MySqlShoppingCartDao(DataSource dataSource)
    {
        super(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        String sql = """
            SELECT
                sc.product_id,
                sc.quantity,
                p.product_id,
                p.name,
                p.price,
                p.category_id,
                p.description,
                p.subcategory,
                p.stock,
                p.image_url,
                p.featured
            FROM shopping_cart sc
            JOIN products p ON p.product_id = sc.product_id
            WHERE sc.user_id = ?
            ORDER BY sc.product_id
            """;

        ShoppingCart cart = new ShoppingCart();

        jdbcTemplate.query(sql, (rs) -> {
            ShoppingCartItem item = mapRowToCartItem(rs);
          cart.add(item);
        }, userId);

        return cart;
    }

    @Override
    public void addOrIncrement(int userId, int productId)
    {
        // Try update first
        int rows = jdbcTemplate.update("""
            UPDATE shopping_cart
            SET quantity = quantity + 1
            WHERE user_id = ? AND product_id = ?
            """, userId, productId);

        // If not present, insert new row
        if (rows == 0)
        {
            jdbcTemplate.update("""
                INSERT INTO shopping_cart (user_id, product_id, quantity)
                VALUES (?, ?, 1)
                """, userId, productId);
        }

    }

    @Override
    public void clear(int userId)
    {
        jdbcTemplate.update("DELETE FROM shopping_cart WHERE user_id = ?", userId);
    }

    private ShoppingCartItem mapRowToCartItem(ResultSet row) throws SQLException
    {
        Product product = new Product();
        product.setProductId(row.getInt("product_id"));
        product.setName(row.getString("name"));
        product.setPrice(row.getBigDecimal("price"));
        product.setCategoryId(row.getInt("category_id"));
        product.setDescription(row.getString("description"));
        product.setSubCategory(row.getString("subcategory"));
        product.setStock(row.getInt("stock"));
        product.setImageUrl(row.getString("image_url"));
        product.setFeatured(row.getBoolean("featured"));

        ShoppingCartItem item = new ShoppingCartItem();
        item.setProductId(product.getProductId());     // if your class tracks it
        item.setProduct(product);
        item.setQuantity(row.getInt("quantity"));
        item.setDiscountPercent(BigDecimal.ZERO);

        return item;
    }
}

