package com.modsen.book.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "patronymic")
    String patronymic;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    List<Book> books;
}
