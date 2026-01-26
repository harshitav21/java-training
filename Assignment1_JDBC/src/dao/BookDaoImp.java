package dao;

import model.Book;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImp implements BookDao {

	@Override
	public List<Book> getAllBooks() throws DaoException {
		List<Book> books = new ArrayList<>();
		String getAllBooksQuery = "SELECT * FROM books";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getAllBooksQuery);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				books.add(new Book(rs.getInt("id"), rs.getString("isbn"), rs.getString("title"), rs.getString("author"),
						rs.getDouble("price")));
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to fetch books", e);
		}
		return books;
	}

	@Override
	public Book addBook(Book book) throws DaoException {
		String addBookQuery = "INSERT INTO books (isbn, title, author, price) VALUES (?,?,?,?)";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(addBookQuery, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.setDouble(4, book.getPrice());

			int rowsAffected = ps.executeUpdate();
			System.out.println(rowsAffected + " rows added successfully!");

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				book.setId(rs.getInt(1));
			}
			return book;
		} catch (SQLException e) {
			throw new DaoException("Failed to add book", e);

		}
	}

	@Override
	public void deleteBook(int id) throws DaoException {
		String deleteBookQuery = "DELETE FROM books WHERE id=?";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(deleteBookQuery)) {

			ps.setInt(1, id);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new DaoException("No book found with id: " + id);
			}
			System.out.println("Book with id " + id + " deleted successfully!");

		} catch (SQLException e) {
			throw new DaoException("Failed to delete book", e);
		}
	}

	@Override
	public void updateBook(int id, Book book) throws DaoException {
		String updateBookQuery = "UPDATE books SET isbn=?, title=?, author=?, price=? WHERE id=?";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateBookQuery)) {

			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.setDouble(4, book.getPrice());
			ps.setInt(5, id);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new DaoException("No book found with id: " + id);
			}
			System.out.println("Book with id " + id + " updated successfully!");

		} catch (SQLException e) {
			throw new DaoException("Failed to update book", e);
		}
	}

	@Override
	public Book getBookById(int id) throws DaoException {
		String getBookByIdQuery = "SELECT * FROM books WHERE id=?";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(getBookByIdQuery)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Book(rs.getInt("id"), rs.getString("isbn"), rs.getString("title"), rs.getString("author"),
						rs.getDouble("price"));
			}
			return null;

		} catch (SQLException e) {
			throw new DaoException("Book not found", e);
		}
	}

}
