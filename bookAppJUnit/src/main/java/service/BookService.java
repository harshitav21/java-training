package service;

import java.util.List;

import dao.BookDao;
import dao.DaoException;
import model.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    private BookDao bookDao;

	public Book addBook(Book book) {
        validateBook(book);
        try {
            Book added = bookDao.addBook(book);
            logger.info("Book added: {}", added.getTitle());
            return added;
        } catch (DaoException e) {
            logger.error("Failed to add book", e);
            throw e; 
        }
    }

    public void updateBook(int id, Book book) {
        validateBook(book);
        try {
            bookDao.updateBook(id, book);
            logger.info("Book updated with id: {}", id);
        } catch (DaoException e) {
            logger.error("Failed to update book with id: {}", id, e);
            throw e;
        }
    }

    public void deleteBook(int id) {
        try {
            bookDao.deleteBook(id);
            logger.info("Book deleted with id: {}", id);
        } catch (DaoException e) {
            logger.error("Failed to delete book with id: {}", id, e);
            throw e;
        }
    }

    public Book getBookById(int id) {
        try {
            return bookDao.getBookById(id);
        } catch (DaoException e) {
            logger.error("Failed to fetch book with id: {}", id, e);
            throw e;
        }
    }

    public List<Book> getAllBooks() {
        try {
            return bookDao.getAllBooks();
        } catch (DaoException e) {
            logger.error("Failed to fetch all books", e);
            throw e;
        }
    }

    private void validateBook(Book book) {
        if (book.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");
        if (book.getTitle() == null || book.getTitle().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
    }
}

