package bookAppJUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.BookDao;
import dao.DaoException;
import model.Book;
import service.BookService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {

	private static final Logger logger = LogManager.getLogger(BookServiceTest.class);

	@Mock
	private BookDao bookDao; // DAO is mocked

	@InjectMocks
	private BookService bookService; // Mockito injects mock into service

	@BeforeAll
	void setupAll() {
		logger.info("Starting BookService tests...");
	}

	@AfterAll
	void tearDownAll() {
		logger.info("Finished BookService tests.");
	}

	@Test
	void testAddBookPositive() {
		Book book = new Book("12345", "JUnit 5 Guide", "John Doe", 29.99);
		Book savedBook = new Book("12345", "JUnit 5 Guide", "John Doe", 29.99);
		savedBook.setId(1);

		when(bookDao.addBook(book)).thenReturn(savedBook);

		Book result = bookService.addBook(book);

		assertNotNull(result);
		assertEquals(1, result.getId());
		verify(bookDao, times(1)).addBook(book);

		logger.info("addBook positive test passed.");
	}

	@Test
	void testAddBookNegativeInvalidPrice() {
		Book book = new Book("12346", "CCNP", "Jane Doe", -5.0);

		assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book));

		logger.info("addBook negative test passed.");
	}

	@Test
	void testGetBookByIdValid() {
		Book book = new Book("12347", "Effective Java", "Joshua Bloch", 45.0);
		book.setId(2);

		when(bookDao.getBookById(2)).thenReturn(book);

		Book result = bookService.getBookById(2);

		assertNotNull(result);
		assertEquals("Effective Java", result.getTitle());
		verify(bookDao).getBookById(2);

		logger.info("getBookById valid test passed.");
	}

	@Test
	void testGetBookByIdInvalid() {
		when(bookDao.getBookById(9999)).thenThrow(new DaoException("Book not found"));

		assertThrows(DaoException.class, () -> bookService.getBookById(9999));

		logger.info("getBookById invalid test passed.");
	}

	@Test
	void testUpdateBookValid() {
		Book updatedBook = new Book("12348", "New Title", "Author A", 25.0);

		doNothing().when(bookDao).updateBook(1, updatedBook);

		assertDoesNotThrow(() -> bookService.updateBook(1, updatedBook));
		verify(bookDao).updateBook(1, updatedBook);

		logger.info("updateBook valid test passed.");
	}

	@Test
	void testUpdateBookInvalid() {
		Book invalidBook = new Book("12349", null, "Author B", -10.0);

		assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(1, invalidBook));

		logger.info("updateBook invalid validation test passed.");
	}

	@Test
	void testDeleteBookValid() {
		doNothing().when(bookDao).deleteBook(1);

		assertDoesNotThrow(() -> bookService.deleteBook(1));
		verify(bookDao).deleteBook(1);

		logger.info("deleteBook valid test passed.");
	}

	@Test
	void testDeleteBookInvalid() {
		doThrow(new DaoException("Book not found")).when(bookDao).deleteBook(8888);

		assertThrows(DaoException.class, () -> bookService.deleteBook(8888));

		logger.info("deleteBook invalid test passed.");
	}

	@Test
	void testGetAllBooks() {
		Book book1 = new Book("111", "Book 1", "Author X", 10.0);
		Book book2 = new Book("112", "Book 2", "Author Y", 20.0);

		when(bookDao.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

		List<Book> books = bookService.getAllBooks();

		assertEquals(2, books.size());
		verify(bookDao).getAllBooks();

		logger.info("getAllBooks test passed.");
	}
}
