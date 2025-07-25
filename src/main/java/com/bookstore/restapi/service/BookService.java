package com.bookstore.restapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.restapi.exception.ResourceNotFoundException;
import com.bookstore.restapi.model.Book;
import com.bookstore.restapi.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public Page<Book> searchBooks(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return bookRepository.findAll(pageable);
        }
        return bookRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        
        book.setTitle(bookDetails.getTitle());
        book.setPrice(bookDetails.getPrice());
        book.setGenre(bookDetails.getGenre());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setPages(bookDetails.getPages());
        book.setLanguage(bookDetails.getLanguage());
        book.setStockQuantity(bookDetails.getStockQuantity());
        book.setImageUrl(bookDetails.getImageUrl());
        book.setAuthor(bookDetails.getAuthor());
        
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
