package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.dto.BookDto;
import com.app.entity.Book;
import com.app.exceptions.DataAccessException;
import com.app.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookDto bookDto) {

        if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new DataAccessException("Book with ISBN already exists!");
        }

        Book book = mapDtoToEntity(bookDto);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new DataAccessException("Book not found with id: " + id));
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByPriceLessThan(Double price) {
        return bookRepository.findByPriceLessThan(price);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public void deleteBookById(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new DataAccessException("Cannot delete. Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public void deleteBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }

    public long getTotalBooksCount() {
        return bookRepository.count();
    }

    private Book mapDtoToEntity(BookDto dto) {
        Book book = new Book();
        book.setId(null);
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        return book;
    }
}
