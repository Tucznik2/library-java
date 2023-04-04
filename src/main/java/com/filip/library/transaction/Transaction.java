package com.filip.library.transaction;

import com.filip.library.book.Book;
import com.filip.library.person.Person;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book_id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_id;

    @Column(name = "checkout_date")
    private Date checkout_date;

    @Column(name = "due_date")
    private Date due_date;

    @Column(name = "return_date")
    private Date return_date;
}
