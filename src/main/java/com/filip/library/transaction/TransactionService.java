package com.filip.library.transaction;

import com.filip.library.book.BookRepository;
import com.filip.library.book.BookRequest;
import com.filip.library.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        newTransaction.setBook(bookRepository.findById(transactionRequest.getBookId()).get());
        newTransaction.setPerson(personRepository.findById(transactionRequest.getPersonId()).get());
        newTransaction.setCheckoutDate(transactionRequest.getCheckoutDate());
        newTransaction.setDueDate(transactionRequest.getDueDate());
        newTransaction.setReturnDate(transactionRequest.getReturnDate());
        transactionRepository.save(newTransaction);
        return newTransaction;
    }
}
