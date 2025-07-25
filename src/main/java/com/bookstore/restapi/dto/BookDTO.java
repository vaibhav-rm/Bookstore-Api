package com.bookstore.restapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    private String genre;
    private String isbn;
    private String description;
    private LocalDate publishedDate;

    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    private String language;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private String imageUrl;
    private Long authorId;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private Long publisherId;
    private String publisherName;
    private Double averageRating;
    private Integer reviewCount;
}
