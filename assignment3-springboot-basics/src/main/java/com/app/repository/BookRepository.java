package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
//	save(Book book)
//	findById(Integer id)
//	findAll()
//	deleteById(Integer id)
//	existsById(Integer id)
//	count()
	List<Book> findByAuthor(String author);

	List<Book> findByPriceLessThan(Double price);

	List<Book> findByCategory(String category);

	boolean existsByIsbn(String isbn);

	void deleteByTitle(String title);

	@Query("SELECT b FROM books b WHERE b.title LIKE %:keyword%")
	List<Book> searchByTitle(String keyword);

	@Query("SELECT b FROM books b ORDER BY b.price DESC")
	List<Book> sortByPriceDesc();

	@Query(value = "SELECT * FROM books WHERE price BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Book> findByPriceRange(double min, double max);
}
