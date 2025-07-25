package com.bookstore.restapi.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bookstore.restapi.model.Book;

public class BookSpecification {

    public static Specification<Book> titleContains(String title) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), 
                "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> descriptionContains(String description) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), 
                "%" + description.toLowerCase() + "%");
    }

    public static Specification<Book> genreEquals(String genre) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(criteriaBuilder.lower(root.get("genre")), 
                genre.toLowerCase());
    }

    public static Specification<Book> authorNameContains(String authorName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("name")), 
                "%" + authorName.toLowerCase() + "%");
    }

    public static Specification<Book> categoryNameContains(String categoryName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), 
                "%" + categoryName.toLowerCase() + "%");
    }

    public static Specification<Book> priceGreaterThanOrEqual(Double price) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Book> priceLessThanOrEqual(Double price) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Book> stockGreaterThan(Integer stock) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThan(root.get("stockQuantity"), stock);
    }
}
