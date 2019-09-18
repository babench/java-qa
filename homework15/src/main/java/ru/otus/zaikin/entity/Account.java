package ru.otus.zaikin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString(exclude = {"password", "books"})
@EqualsAndHashCode(exclude = "books")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String username;
    @JsonIgnore
    public String password;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Account(String name, String password) {
        this.username = name;
        this.password = password;
    }


    public List<Book> getBooks() {
        //return Collections.unmodifiableList(this.books);
        return this.books;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}