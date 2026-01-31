package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	        return service.addBook(dto);
	    }

	    @GetMapping
	    public List<Book> getAll() {
	        return service.getAll();
	    }

	    @GetMapping("/{id}")
	    public Book getById(@PathVariable Integer id) {
	        return service.getById(id);
	    }

	    @PutMapping("/{id}")
	    public Book update(@PathVariable Integer id, @RequestBody BookDto dto) {
	        return service.update(id, dto);
	    }

	    @DeleteMapping("/{id}")
	    public String delete(@PathVariable Integer id) {
	        service.delete(id);
	        return "Deleted book with id " + id;
	    }
}
