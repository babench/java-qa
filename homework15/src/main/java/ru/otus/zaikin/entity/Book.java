package ru.otus.zaikin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString(exclude = "account")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String name;
    public String author;
    public String description;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Account account;

    public Book(Account account, String name, String author, String description) {
        this.account = account;
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}