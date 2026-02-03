package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.app.dto.BookDto;
import com.app.entity.Book;
import com.app.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public Book create(@RequestBody BookDto dto) {
        return service.saveBook(dto);
    }

    @GetMapping
    public List<Book> getAll() {
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable int id) {
        return service.getBookById(id);
    }

    @GetMapping("/author/{author}")
    public List<Book> getByAuthor(@PathVariable String author) {
        return service.getBooksByAuthor(author);
    }

    @GetMapping("/price")
    public List<Book> getByPriceRange(
            @RequestParam("min") double min,
            @RequestParam("max") double max) {
        return service.getBooksByPriceRange(min, max);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable int id, @RequestBody BookDto dto) {
        return service.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.deleteBookById(id);
        return "Deleted book with id " + id;
    }
}
