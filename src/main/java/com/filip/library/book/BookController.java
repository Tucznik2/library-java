package com.filip.library.book;

import com.filip.library.author.Author;
import com.filip.library.author.AuthorRepository;
import com.filip.library.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDto book) {
        if (book.getTitle() == null || book.getAuthors() == null || book.getAuthors().isEmpty() || book.getIsbn() == null || book.getPublished_date() == null) {
            return ResponseEntity.badRequest().body(book);
        }
        List<Author> authors = new ArrayList<>();
        for (Long authorId : book.getAuthors()) {
            authorRepository.findById(authorId).ifPresent(authors::add);
        }
        Book book1 = new Book(book.getTitle(), authors, book.getIsbn(), book.getPublished_date(), false);

        Book createdBook = BookService.saveBook(book1);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Book bookToUpdate = bookData.get();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthors(book.getAuthors());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublished_date(book.getPublished_date());
        Book updatedbook = bookRepository.save(bookToUpdate);
        return ResponseEntity.ok(updatedbook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> bookToDelete = bookRepository.findById(id);
        if (bookToDelete.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
