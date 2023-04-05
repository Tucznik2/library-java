package com.filip.library.author;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondNamme;

    @Column(name = "country")
    private String country;

    public Author(Long id, String firstName, String secondNamme, String country) {
        this.id = id;
        this.firstName = firstName;
        this.secondNamme = secondNamme;
        this.country = country;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondNamme() {
        return secondNamme;
    }

    public void setSecondNamme(String secondNamme) {
        this.secondNamme = secondNamme;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
