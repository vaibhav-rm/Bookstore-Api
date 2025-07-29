package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Schema(description = "Author entity representing a book author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the author", example = "1")
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false)
    @Schema(description = "First name of the author", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false)
    @Schema(description = "Last name of the author", example = "Doe")
    private String lastName;

    @Column(name = "birth_date")
    @Schema(description = "Birth date of the author", example = "1965-07-31")
    private LocalDate birthDate;

    @Size(max = 1000, message = "Biography must not exceed 1000 characters")
    @Column(name = "biography", length = 1000)
    @Schema(description = "Biography of the author", example = "John Doe is a renowned author...")
    private String biography;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Handles serialization for the 'one' side of the relationship
    @Schema(description = "List of books written by this author")
    private List<Book> books = new ArrayList<>();

    // Constructors
    public Author() {}

    public Author(String firstName, String lastName, LocalDate birthDate, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.biography = biography;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
