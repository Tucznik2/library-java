package com.filip.library.transaction;

import com.filip.library.book.Book;
import com.filip.library.book.BookRepository;
import com.filip.library.person.Person;
import com.filip.library.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PersonRepository personRepository;
    public Transaction saveTransaction(TransactionRequest transactionRequest){
        Transaction newTransaction = new Transaction();
        if(bookRepository.findById(transactionRequest.getBookId()).isEmpty() || personRepository.findById(transactionRequest.getPersonId()).isEmpty()){
            ResponseEntity.notFound().build();
        }
        Book bookToRent = bookRepository.findById(transactionRequest.getBookId()).get();
        Person borrower = personRepository.findById(transactionRequest.getPersonId()).get();
        bookToRent.setAvailable(false);
        newTransaction.setBook(bookToRent);
        newTransaction.setPerson(borrower);
        newTransaction.setCheckoutDate(transactionRequest.getCheckoutDate());
        newTransaction.setDueDate(transactionRequest.getDueDate());
        newTransaction.setReturnDate(transactionRequest.getReturnDate());
        transactionRepository.save(newTransaction);
        return newTransaction;
    }
}
