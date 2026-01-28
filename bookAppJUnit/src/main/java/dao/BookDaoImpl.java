package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Book;
import util.JPAUtil;

public class BookDaoImpl implements BookDao{
	@Override
	public List<Book> getAllBooks() {
		try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("from Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Failed to fetch all books", e);
        }
	}

	@Override
	public Book addBook(Book book) {
		try (EntityManager em = JPAUtil.getEntityManager()) {
		    em.getTransaction().begin();
		    em.persist(book);
		    em.getTransaction().commit();
		} catch (Exception e) {
		    throw new DaoException("Failed to add book", e);
		}
		return book;
	}

	@Override
	public void deleteBook(int id) {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			em.getTransaction().begin();
			Book book = em.find(Book.class, id);
			if (book != null)
				em.remove(book);
			em.getTransaction().commit();
		}catch(Exception e) {
			throw new DaoException("Failed to delete book", e);
		}
	}

	@Override
	public void updateBook(int id, Book book) {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			em.getTransaction().begin();
			Book existing = em.find(Book.class, id);
			if (existing != null) {
				existing.setIsbn(book.getIsbn());
				existing.setTitle(book.getTitle());
				existing.setAuthor(book.getAuthor());
				existing.setPrice(book.getPrice());
			}
			em.getTransaction().commit();
		}catch(Exception e) {
			throw new DaoException("Failed to update book", e);
		}

	}

	@Override
	public Book getBookById(int id) {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			return em.find(Book.class, id);
		}catch(Exception e) {
			throw new DaoException("Failed to fetch book", e);
		}
	}

}
