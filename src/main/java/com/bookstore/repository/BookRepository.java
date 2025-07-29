package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.bookstore.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    Page<Book> findByGenre(Genre genre, Pageable pageable);
    
    Page<Book> findByAuthorId(Long authorId, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    Page<Book> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, 
                                  @Param("maxPrice") BigDecimal maxPrice, 
                                  Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:genre IS NULL OR b.genre = :genre) AND " +
           "(:authorId IS NULL OR b.author.id = :authorId) AND " +
           "(:minPrice IS NULL OR b.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR b.price <= :maxPrice)")
    Page<Book> findBooksWithFilters(@Param("title") String title,
                                   @Param("genre") Genre genre,
                                   @Param("authorId") Long authorId,
                                   @Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice,
                                   Pageable pageable);
    
    List<Book> findByStockQuantityLessThan(Integer quantity);
}
