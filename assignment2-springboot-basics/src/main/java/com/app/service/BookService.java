package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.dto.BookDto;
import com.app.entity.Book;
import com.app.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book addBook(BookDto dto) {
    	  Book book = new Book();
          book.setTitle(dto.getTitle());
          book.setAuthor(dto.getAuthor());
          book.setPrice(dto.getPrice());
          return repository.save(book);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(Integer id) {
        return repository.findById(id);
    }

    public Book update(Integer id, BookDto dto) {
        Book existing = repository.findById(id);
        if (existing != null) {
            existing.setTitle(dto.getTitle());
            existing.setAuthor(dto.getAuthor());
            existing.setPrice(dto.getPrice());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
