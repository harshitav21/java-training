package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.dao.BookJdbcDao;
import com.app.dao.BookNamedDao;
import com.app.dto.BookDto;
import com.app.entity.Book;
import com.app.exceptions.DataAccessException;

@Service
public class BookService {

    private final BookJdbcDao jdbcDao;
    private final BookNamedDao namedDao;

    public BookService(BookJdbcDao jdbcDao, BookNamedDao namedDao) {
        this.jdbcDao = jdbcDao;
        this.namedDao = namedDao;
    }

    public Book saveBook(BookDto dto) {
        Book book = mapDtoToEntity(dto);

        List<Book> allBooks = jdbcDao.findAll();
        boolean exists = allBooks.stream()
                .anyMatch(b -> b.getIsbn().equals(book.getIsbn()));

        if (exists) {
            throw new DataAccessException("Book with ISBN already exists");
        }

        int generatedId = jdbcDao.save(book);
        book.setId(generatedId);

        return book;
    }

    public List<Book> getAllBooks() {
        return jdbcDao.findAll(); 
    }

    public Book getBookById(int id) {
        try {
            return jdbcDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Book not found with id: " + id);
        }
    }

    public List<Book> getBooksByAuthor(String author) {
        return namedDao.findByAuthor(author);
    }

    public List<Book> getBooksByPriceRange(double min, double max) {
        return namedDao.findByPriceRange(min, max);
    }

    public void deleteBookById(int id) {
        try {
            @SuppressWarnings("unused")
			Book b = jdbcDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Cannot delete. Book not found with id: " + id);
        }

        jdbcDao.delete(id);
    }

    public Book updateBook(int id, BookDto dto) {
        Book book = mapDtoToEntity(dto);
        book.setId(id);

        int rows = jdbcDao.update(book);
        if (rows == 0) {
            throw new DataAccessException("Book not found with id: " + id);
        }

        return book;
    }

    private Book mapDtoToEntity(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        return book;
    }
}
