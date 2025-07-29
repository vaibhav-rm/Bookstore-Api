package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Schema(description = "Book entity representing a book in the bookstore")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the book", example = "1")
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    @Schema(description = "Title of the book", example = "The Great Gatsby")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Column(name = "isbn", unique = true, nullable = false)
    @Schema(description = "ISBN of the book", example = "978-0747532699")
    private String isbn;

    @Column(name = "publication_date")
    @Schema(description = "Publication date of the book", example = "1925-04-10")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    @Schema(description = "Genre of the book", example = "FICTION")
    private Genre genre;

    @Column(name = "price", precision = 8, scale = 2)
    @Schema(description = "Price of the book", example = "12.99")
    private BigDecimal price;

    @Column(name = "description", length = 2000)
    @Schema(description = "Description of the book", example = "A classic American novel...")
    private String description;

    @Column(name = "stock_quantity")
    @Schema(description = "Stock quantity available", example = "50")
    private Integer stockQuantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference // Handles serialization for the 'many' side of the relationship
    @Schema(description = "Author of the book")
    private Author author;

    // Constructors
    public Book() {}

    public Book(String title, String isbn, LocalDate publicationDate, Genre genre, 
                BigDecimal price, String description, Integer stockQuantity, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
