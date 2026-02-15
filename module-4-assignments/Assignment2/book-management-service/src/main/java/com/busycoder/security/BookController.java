package com.busycoder.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1, "Spring Security in Action", "Laurentiu Spilca"));
        books.add(new Book(2, "Spring Boot Up & Running", "Mark Heckler"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public List<Book> getAllBooks() {
        return books;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public Book createBook(@RequestBody Book book) {
        this.books.add(book);
        return book;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
         Book existingBook = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
         
         if (existingBook != null) {
             existingBook.setTitle(updatedBook.getTitle());
             existingBook.setAuthor(updatedBook.getAuthor());
         }
         return existingBook;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String deleteBook(@PathVariable Integer id) {
        boolean removed = books.removeIf(b -> b.getId().equals(id));
        if (removed) {
            return "Book deleted successfully";
        } else {
            return "Book not found";
        }
    }
}
