package com.filip.library.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        if (author.getCountry() == null || author.getFirstName() == null || author.getSecondName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Author createdAuthor;
        createdAuthor = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Optional<Author> authorData = authorRepository.findById(id);
        if (authorData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Author authorToUpdate = authorData.get();
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setSecondName(author.getSecondName());
        authorToUpdate.setCountry(author.getCountry());
        Author updatedAuthor = authorRepository.save(authorToUpdate);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        Optional<Author> authorToDelete = authorRepository.findById(id);
        if (authorToDelete.isPresent()) {
            authorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
