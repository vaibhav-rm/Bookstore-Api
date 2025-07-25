package com.bookstore.restapi.controller;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.restapi.model.Book;
import com.bookstore.restapi.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Books", description = "Book management operations")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieve a paginated list of books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved books"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping
    public Page<Book> getBooks(
            @Parameter(description = "Search term for title") @RequestParam(defaultValue = "") String search,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return bookService.searchBooks(search, pageable);
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Book ID") @PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the bookstore")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }

    @Operation(summary = "Update a book", description = "Update an existing book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book updated successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found"),
        @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "Book ID") @PathVariable Long id, 
            @Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the bookstore")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
