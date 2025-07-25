package com.bookstore.restapi.repository;

import com.bookstore.restapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
}
