package service;

import dao.BookDao;
import dao.BookDaoJpaImpl;
import model.Book;

import java.util.List;

public class BookService {

	private BookDao bookDao = new BookDaoJpaImpl();

	public Book addBook(Book book) throws Exception {
		if (book.getPrice() <= 0)
			throw new Exception("Price must be greater than zero");
		if (book.getTitle() == null || book.getTitle().isEmpty())
			throw new Exception("Title cannot be empty");

		return bookDao.addBook(book);
	}

	public List<Book> listBooks() {
		return bookDao.getAllBooks();
	}

	public Book findBook(int id) {
		return bookDao.getBookById(id);
	}

	public void deleteBook(int id) {
		bookDao.deleteBook(id);
	}

	public void updateBook(int id, Book book) throws Exception {
		if (book.getPrice() <= 0)
			throw new Exception("Price must be greater than zero");
		if (book.getTitle() == null || book.getTitle().isEmpty())
			throw new Exception("Title cannot be empty");

		bookDao.updateBook(id, book);
	}
}
