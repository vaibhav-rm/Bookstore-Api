package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Genre;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    public Book createBook(Book book) {
        // Ensure the author exists
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            authorService.getAuthorById(book.getAuthor().getId());
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setGenre(bookDetails.getGenre());
        book.setPrice(bookDetails.getPrice());
        book.setDescription(bookDetails.getDescription());
        book.setStockQuantity(bookDetails.getStockQuantity());
        
        if (bookDetails.getAuthor() != null && bookDetails.getAuthor().getId() != null) {
            book.setAuthor(authorService.getAuthorById(bookDetails.getAuthor().getId()));
        }
        
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public Page<Book> searchBooks(String title, Genre genre, Long authorId, 
                                 BigDecimal minPrice, BigDecimal maxPrice, 
                                 Pageable pageable) {
        return bookRepository.findBooksWithFilters(title, genre, authorId, minPrice, maxPrice, pageable);
    }

    public Page<Book> getBooksByGenre(Genre genre, Pageable pageable) {
        return bookRepository.findByGenre(genre, pageable);
    }

    public Page<Book> getBooksByAuthor(Long authorId, Pageable pageable) {
        return bookRepository.findByAuthorId(authorId, pageable);
    }

    public List<Book> getLowStockBooks(Integer threshold) {
        return bookRepository.findByStockQuantityLessThan(threshold);
    }
}
