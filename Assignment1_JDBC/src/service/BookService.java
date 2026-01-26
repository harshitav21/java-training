package service;

import java.util.List;

import dao.BookDao;
import dao.BookDaoImp;
import dao.DaoException;
import model.Book;

public class BookService {
	private BookDao bookDao = new BookDaoImp();

	public Book addBook(Book book) throws Exception {
		if (book.getPrice() <= 0)
			throw new Exception("Price must be greater than zero");
		if (book.getTitle() == null || book.getTitle().isEmpty())
			throw new Exception("Title cannot be empty");

		return bookDao.addBook(book);
	}

	public List<Book> listBooks() throws DaoException {
		return bookDao.getAllBooks();
	}

	public Book findBook(int id) throws DaoException {
		return bookDao.getBookById(id);
	}

	public void deleteBook(int id) throws DaoException {
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
