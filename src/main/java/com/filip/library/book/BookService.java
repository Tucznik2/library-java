package com.filip.library.book;

import com.filip.library.author.Author;
import com.filip.library.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    public Book saveBook(BookRequest book){
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAvailable(true);
        newBook.setPublishedDate(book.getPublishedDate());
        newBook.setIsbn(book.getIsbn());
        Set<Author> authorSet = new HashSet<>();
        for (Long id:
             book.getAuthorsId()) {
            Author author = authorRepository.findById(id).get();
            authorSet.add(author);
        }
        newBook.setAuthors(authorSet);
        bookRepository.save(newBook);
        return newBook;
    }
}
