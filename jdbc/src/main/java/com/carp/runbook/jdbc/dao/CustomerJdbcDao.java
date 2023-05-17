package com.carp.runbook.jdbc.dao;

import com.carp.runbook.jdbc.entity.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CustomerJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", new BeanPropertyRowMapper<>(Customer.class));
    }

    public Customer findById(long id) {
        return jdbcTemplate.queryForObject("select * from customer where id = ?", new CustomerRowMapper(), id);
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from customer where id = ?", id);
    }

    public int insert(Customer newCustomer) {
        return jdbcTemplate.update("insert into customer(id, name, created_at) values(?, ?, ?)",
                newCustomer.getId(), newCustomer.getName(), new Timestamp(newCustomer.getCreatedAt().toEpochMilli()));
    }

    public int update(Customer newCustomer) {
        return jdbcTemplate.update("update customer set name = ? where id = ?", newCustomer.getName(), newCustomer.getId());
    }

    class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("created_at").toInstant());
        }
    }
}
