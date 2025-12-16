package org.yearup.data.mysql;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    // =========================
    // GET ALL CATEGORIES
    // =========================
    @Override
    public List<Category> getAll()
    {
        String sql = """
            SELECT category_id, name, description
            FROM categories
            ORDER BY category_id
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    // =========================
    // GET CATEGORY BY ID
    // =========================
    @Override
    public Category getById(int categoryId)
    {
        String sql = """
            SELECT category_id, name, description
            FROM categories
            WHERE category_id = ?
            """;

        try
        {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> mapRow(rs),
                    categoryId
            );
        }
        catch (EmptyResultDataAccessException ex)
        {
            return null;
        }
    }

    // =========================
    // CREATE CATEGORY
    // =========================
    @Override
    public Category create(Category category)
    {
        String sql = """
            INSERT INTO categories (name, description)
            VALUES (?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection ->
        {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            return ps;
        }, keyHolder);

        int newId = keyHolder.getKey().intValue();
        return getById(newId);
    }

    // =========================
    // UPDATE CATEGORY
    // =========================
    @Override
    public void update(int categoryId, Category category)
    {
        String sql = """
            UPDATE categories
            SET name = ?, description = ?
            WHERE category_id = ?
            """;

        jdbcTemplate.update(
                sql,
                category.getName(),
                category.getDescription(),
                categoryId
        );
    }

    // =========================
    // DELETE CATEGORY
    // =========================
    @Override
    public void delete(int categoryId)
    {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        jdbcTemplate.update(sql, categoryId);
    }

    // =========================
    // ROW MAPPER
    // =========================
    private Category mapRow(ResultSet row) throws SQLException
    {
        Category category = new Category();
        category.setCategoryId(row.getInt("category_id"));
        category.setName(row.getString("name"));
        category.setDescription(row.getString("description"));
        return category;
    }
}
