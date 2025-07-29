package com.bookstore.controller;

import com.bookstore.entity.Author;
import com.bookstore.repository.AuthorRepository;
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
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "Author management operations")
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Operation(
        summary = "Get all authors",
        description = "Retrieve a complete list of all authors in the bookstore database"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all authors",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Author.class)
            )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

    @Operation(
        summary = "Get author by ID",
        description = "Retrieve a specific author using their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Author found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Author.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Author not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(
            @Parameter(description = "Unique identifier of the author", example = "1") 
            @PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Create new author",
        description = "Add a new author to the bookstore database"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Author created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Author.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided")
    })
    @PostMapping
    public ResponseEntity<Author> createAuthor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Author object to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = Author.class))
            )
            @RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @Operation(
        summary = "Update existing author",
        description = "Update an existing author's information using their ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Author updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Author.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Author not found with the provided ID"),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @Parameter(description = "Unique identifier of the author to update", example = "1") 
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated author information",
                required = true,
                content = @Content(schema = @Schema(implementation = Author.class))
            )
            @RequestBody Author authorDetails) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setFirstName(authorDetails.getFirstName());
            author.setLastName(authorDetails.getLastName());
            author.setBirthDate(authorDetails.getBirthDate());
            author.setBiography(authorDetails.getBiography());
            Author updatedAuthor = authorRepository.save(author);
            return ResponseEntity.ok(updatedAuthor);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Delete author",
        description = "Remove an author from the bookstore database using their ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Author not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "Unique identifier of the author to delete", example = "1") 
            @PathVariable Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
