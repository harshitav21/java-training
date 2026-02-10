package com.app.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.app.entity.Book;
import com.app.jdbcrowmapper.BookRowMapper;

@Repository
public class BookJdbcDao {
	private final JdbcTemplate jdbcTemplate;
	private final BookRowMapper bookRowMapper;

	public BookJdbcDao(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.bookRowMapper = bookRowMapper;
	}

	public int save(Book b) {
		String sql = """
				    INSERT INTO books (title, author, price, isbn, category)
				    VALUES (?, ?, ?, ?, ?)
				""";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, b.getTitle());
			ps.setString(2, b.getAuthor());
			ps.setDouble(3, b.getPrice());
			ps.setString(4, b.getIsbn());
			ps.setString(5, b.getCategory());
			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public Book findById(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id = ?", bookRowMapper, id);
	}

	public List<Book> findAll() {
		return jdbcTemplate.query("SELECT * FROM books", bookRowMapper);
	}

	public int update(Book b) {
		return jdbcTemplate.update("UPDATE books SET title=?, author=?, price=?, isbn=?, category=? WHERE id=?",
				b.getTitle(), b.getAuthor(), b.getPrice(), b.getIsbn(), b.getCategory(), b.getId());
	}

	public int delete(int id) {
		return jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
	}
}
