package com.bookstore.config;

import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import com.bookstore.entity.Genre;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create Authors
        Author author1 = new Author("J.K.", "Rowling", 
                LocalDate.of(1965, 7, 31), 
                "British author, best known for the Harry Potter series");
        
        Author author2 = new Author("George", "Orwell", 
                LocalDate.of(1903, 6, 25), 
                "English novelist and essayist, journalist and critic");
        
        Author author3 = new Author("Jane", "Austen", 
                LocalDate.of(1775, 12, 16), 
                "English novelist known for her wit and social commentary");

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        // Create Books
        Book book1 = new Book("Harry Potter and the Philosopher's Stone", 
                "978-0747532699", 
                LocalDate.of(1997, 6, 26), 
                Genre.FANTASY, 
                new BigDecimal("12.99"), 
                "The first book in the Harry Potter series", 
                50, 
                author1);

        Book book2 = new Book("1984", 
                "978-0451524935", 
                LocalDate.of(1949, 6, 8), 
                Genre.SCIENCE_FICTION, 
                new BigDecimal("13.99"), 
                "A dystopian social science fiction novel", 
                30, 
                author2);

        Book book3 = new Book("Pride and Prejudice", 
                "978-0141439518", 
                LocalDate.of(1813, 1, 28), 
                Genre.ROMANCE, 
                new BigDecimal("10.99"), 
                "A romantic novel of manners", 
                25, 
                author3);

        Book book4 = new Book("Animal Farm", 
                "978-0451526342", 
                LocalDate.of(1945, 8, 17), 
                Genre.FICTION, 
                new BigDecimal("9.99"), 
                "An allegorical novella about farm animals", 
                40, 
                author2);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
    }
}
