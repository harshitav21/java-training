package com.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.entity.Book;
import com.app.jdbcrowmapper.BookRowMapper;

@Repository
public class BookNamedDao {
	private final NamedParameterJdbcTemplate named;

	private final BookRowMapper rowMapper;

	public BookNamedDao(NamedParameterJdbcTemplate named, BookRowMapper rowMapper) {
		this.named = named;
		this.rowMapper = rowMapper;
	}

	public List<Book> findByAuthor(String author) {
		String sql = "SELECT * FROM books WHERE author = :author";

		Map<String, Object> params = new HashMap<>();
		params.put("author", author);

		return named.query(sql, params, rowMapper);
	}

	public List<Book> findByPriceRange(double min, double max) {
		String sql = "SELECT * FROM books WHERE price BETWEEN :min AND :max";

		Map<String, Object> params = new HashMap<>();
		params.put("min", min);
		params.put("max", max);

		return named.query(sql, params, rowMapper);
	}
}
