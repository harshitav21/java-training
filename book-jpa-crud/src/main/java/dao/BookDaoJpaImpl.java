package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Book;
import util.JPAUtil;

public class BookDaoJpaImpl implements BookDao {

	@Override
	public List<Book> getAllBooks() {
		EntityManager em = JPAUtil.getEntityManager();
		return em.createQuery("from Book", Book.class).getResultList();
	}

	@Override
	public Book addBook(Book book) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(book);
		em.getTransaction().commit();
		em.close();
		return book;
	}

	@Override
	public void deleteBook(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Book book = em.find(Book.class, id);
		if (book != null)
			em.remove(book);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void updateBook(int id, Book book) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		Book existing = em.find(Book.class, id);
		if (existing != null) {
			existing.setIsbn(book.getIsbn());
			existing.setTitle(book.getTitle());
			existing.setAuthor(book.getAuthor());
			existing.setPrice(book.getPrice());
		}
		em.getTransaction().commit();
		em.close();

	}

	@Override
	public Book getBookById(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		return em.find(Book.class, id);
	}

}
