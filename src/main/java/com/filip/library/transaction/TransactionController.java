package com.filip.library.transaction;

import com.filip.library.book.BookRepository;
import com.filip.library.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable Long id){
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest){
        Transaction createdTransaction = transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest){
        Optional<Transaction> transactionData = transactionRepository.findById(id);
        if(transactionData.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Transaction transactionToUpdate = transactionData.get();
        transactionToUpdate.setBook(bookRepository.findById(transactionRequest.getBookId()).get());
        transactionToUpdate.setPerson(personRepository.findById(transactionRequest.getBookId()).get());
        transactionToUpdate.setCheckoutDate(transactionRequest.getCheckoutDate());
        transactionToUpdate.setDueDate(transactionRequest.getDueDate());
        transactionToUpdate.setReturnDate(transactionRequest.getReturnDate());
        Transaction updatedTransaction = transactionRepository.save(transactionToUpdate);
        return ResponseEntity.ok(updatedTransaction);
    }
}
