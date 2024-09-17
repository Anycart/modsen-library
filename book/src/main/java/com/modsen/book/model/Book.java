package com.modsen.book.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "isbn", unique = true)
    String isbn;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "genre", nullable = false)
    String genre;
    @Column(name = "description")
    String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    Author author;
}
