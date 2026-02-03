package com.app.jdbcrowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.app.entity.Book;

@Component
public class BookRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setTitle(rs.getString("title"));
		b.setAuthor(rs.getString("author"));
		b.setPrice(rs.getDouble("price"));
		b.setIsbn(rs.getString("isbn"));
		 b.setCategory(rs.getString("category"));
		return b;
	}

}
