package com.filip.library.book;

import com.filip.library.author.Author;
import com.filip.library.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
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
    public ResponseEntity<Book> createBook(@RequestBody BookRequest book) {
        Book createdBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookRequest book) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Book bookToUpdate = bookData.get();
        bookToUpdate.setTitle(book.getTitle());
        Set<Author> authorSet = new HashSet<>();
        for (Long authorId :
                book.getAuthorsId()) {
            Author author = authorRepository.findById(authorId).get();
            authorSet.add(author);
        }
        bookToUpdate.setAuthors(authorSet);
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublishedDate(book.getPublishedDate());
        bookToUpdate.setAvailable(book.isAvailable());
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
