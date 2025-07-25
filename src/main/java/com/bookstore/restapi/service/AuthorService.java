package com.bookstore.restapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.restapi.exception.ResourceNotFoundException;
import com.bookstore.restapi.model.Author;
import com.bookstore.restapi.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Page<Author> getAllAuthors(String search, Pageable pageable) {
        if (search.isEmpty()) {
            return authorRepository.findAll(pageable);
        }
        return authorRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        
        author.setName(authorDetails.getName());
        author.setBiography(authorDetails.getBiography());
        author.setBirthDate(authorDetails.getBirthDate());
        author.setNationality(authorDetails.getNationality());
        author.setEmail(authorDetails.getEmail());
        author.setWebsite(authorDetails.getWebsite());
        
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
}
