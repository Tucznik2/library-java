package com.filip.library.book;

public class BookService {
    public static Book saveBook(Book book) {
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthors(book.getAuthors());
        newBook.setAvailable(true);
        newBook.setPublished_date(book.getPublished_date());
        newBook.setIsbn(book.getIsbn());
        return newBook;
    }
}
