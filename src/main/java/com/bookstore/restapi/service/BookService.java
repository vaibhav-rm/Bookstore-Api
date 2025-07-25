package com.bookstore.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.restapi.dto.BookDTO;
import com.bookstore.restapi.exception.ResourceNotFoundException;
import com.bookstore.restapi.model.Book;
import com.bookstore.restapi.repository.BookRepository;
import com.bookstore.restapi.specification.BookSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public Page<BookDTO> searchBooks(String search, String genre, String author, String category, 
                                   Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Book> spec = Specification.where(null);
        
        if (!search.isEmpty()) {
            spec = spec.and(BookSpecification.titleContains(search)
                    .or(BookSpecification.descriptionContains(search)));
        }
        if (!genre.isEmpty()) {
            spec = spec.and(BookSpecification.genreEquals(genre));
        }
        if (!author.isEmpty()) {
            spec = spec.and(BookSpecification.authorNameContains(author));
        }
        if (!category.isEmpty()) {
            spec = spec.and(BookSpecification.categoryNameContains(category));
        }
        if (minPrice != null) {
            spec = spec.and(BookSpecification.priceGreaterThanOrEqual(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(BookSpecification.priceLessThanOrEqual(maxPrice));
        }
        
        Page<Book> books = bookRepository.findAll(spec, pageable);
        List<BookDTO> bookDTOs = books.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(bookDTOs, pageable, books.getTotalElements());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return convertToDTO(book);
    }

    public BookDTO createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    public BookDTO updateBook(Long id, Book bookDetails) {
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
        book.setCategory(bookDetails.getCategory());
        book.setPublisher(bookDetails.getPublisher());
        
        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public Page<BookDTO> getFeaturedBooks(Pageable pageable) {
        // Logic to get featured books (e.g., high-rated books)
        Page<Book> books = bookRepository.findFeaturedBooks(pageable);
        List<BookDTO> bookDTOs = books.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(bookDTOs, pageable, books.getTotalElements());
    }

    public Page<BookDTO> getBestsellers(Pageable pageable) {
        // Logic to get bestselling books
        Page<Book> books = bookRepository.findBestsellers(pageable);
        List<BookDTO> bookDTOs = books.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(bookDTOs, pageable, books.getTotalElements());
    }

    public void updateStock(Long id, Integer quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        book.setStockQuantity(quantity);
        bookRepository.save(book);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setGenre(book.getGenre());
        dto.setIsbn(book.getIsbn());
        dto.setDescription(book.getDescription());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setPages(book.getPages());
        dto.setLanguage(book.getLanguage());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setImageUrl(book.getImageUrl());
        
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }
        
        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getId());
            dto.setCategoryName(book.getCategory().getName());
        }
        
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getId());
            dto.setPublisherName(book.getPublisher().getName());
        }
        
        // Calculate average rating and review count
        if (book.getReviews() != null && !book.getReviews().isEmpty()) {
            double avgRating = book.getReviews().stream()
                    .mapToInt(review -> review.getRating())
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(Math.round(avgRating * 10.0) / 10.0);
            dto.setReviewCount(book.getReviews().size());
        } else {
            dto.setAverageRating(0.0);
            dto.setReviewCount(0);
        }
        
        return dto;
    }
}
