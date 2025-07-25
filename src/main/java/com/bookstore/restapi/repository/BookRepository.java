package com.bookstore.restapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.restapi.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    
    Page<Book> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
    
    Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
    
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    Page<Book> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    
    @Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b ORDER BY AVG(r.rating) DESC")
    Page<Book> findFeaturedBooks(Pageable pageable);
    
    @Query("SELECT b FROM Book b LEFT JOIN b.orderItems oi GROUP BY b ORDER BY SUM(oi.quantity) DESC")
    Page<Book> findBestsellers(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.stockQuantity > 0")
    Page<Book> findAvailableBooks(Pageable pageable);
}
