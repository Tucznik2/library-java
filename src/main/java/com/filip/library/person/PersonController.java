package com.filip.library.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/people")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<Person>> getAllAuthors() {
        List<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getAuthorById(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Person> createAuthor(@RequestBody Person person) {
        if (person.getFirstName() == null || person.getSecondName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Person createdPerson;
        createdPerson = personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updateAuthor(@PathVariable Long id, @RequestBody Person person) {
        Optional<Person> personData = personRepository.findById(id);
        if (personData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Person personToUpdate = personData.get();
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setSecondName(person.getSecondName());
        Person updatedAuthor = personRepository.save(personToUpdate);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        Optional<Person> personToDelete = personRepository.findById(id);
        if (personToDelete.isPresent()) {
            personRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
