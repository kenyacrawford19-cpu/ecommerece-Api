package org.yearup.data.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class MySqlDaoBase
{
    protected final DataSource dataSource;
    protected final JdbcTemplate jdbcTemplate;

    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public abstract List<Category> getAllCategories();
}

