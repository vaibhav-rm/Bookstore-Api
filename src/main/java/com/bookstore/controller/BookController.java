package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management operations")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Operation(
        summary = "Get all books",
        description = "Retrieve a complete list of all books in the bookstore inventory"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all books",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @Operation(
        summary = "Get book by ID",
        description = "Retrieve a specific book using its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Book found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Book not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Unique identifier of the book", example = "1") 
            @PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Get book by ISBN",
        description = "Retrieve a specific book using its ISBN number"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Book found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Book not found with the provided ISBN")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(
            @Parameter(description = "ISBN of the book", example = "978-0747532699") 
            @PathVariable String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Create new book",
        description = "Add a new book to the bookstore inventory"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Book created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Book object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = Book.class))
            )
            @RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(savedBook);
    }

    @Operation(
        summary = "Update existing book",
        description = "Update an existing book's information using its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Book updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Book not found with the provided ID"),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "Unique identifier of the book to update", example = "1") 
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated book information",
                required = true,
                content = @Content(schema = @Schema(implementation = Book.class))
            )
            @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setIsbn(bookDetails.getIsbn());
            book.setPublicationDate(bookDetails.getPublicationDate());
            book.setGenre(bookDetails.getGenre());
            book.setPrice(bookDetails.getPrice());
            book.setDescription(bookDetails.getDescription());
            book.setStockQuantity(bookDetails.getStockQuantity());
            if (bookDetails.getAuthor() != null) {
                book.setAuthor(bookDetails.getAuthor());
            }
            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok(updatedBook);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Delete book",
        description = "Remove a book from the bookstore inventory using its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Unique identifier of the book to delete", example = "1") 
            @PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
