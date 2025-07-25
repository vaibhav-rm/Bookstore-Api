package com.bookstore.restapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.restapi.model.Author;
import com.bookstore.restapi.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Authors", description = "Author management operations")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Get all authors", description = "Retrieve a paginated list of authors")
    @GetMapping
    public Page<Author> getAllAuthors(
            @Parameter(description = "Search term for author name") @RequestParam(defaultValue = "") String search,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return authorService.getAllAuthors(search, pageable);
    }

    @Operation(summary = "Get author by ID", description = "Retrieve a specific author by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Author found"),
        @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@Parameter(description = "Author ID") @PathVariable Long id) {
        return authorService.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new author", description = "Add a new author to the system")
    @PostMapping
    public Author createAuthor(@Valid @RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @Operation(summary = "Update an author", description = "Update an existing author")
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @Parameter(description = "Author ID") @PathVariable Long id, 
            @Valid @RequestBody Author author) {
        return ResponseEntity.ok(authorService.updateAuthor(id, author));
    }

    @Operation(summary = "Delete an author", description = "Remove an author from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@Parameter(description = "Author ID") @PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
