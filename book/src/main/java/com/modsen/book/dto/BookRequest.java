package com.modsen.book.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String name;
    String isbn;
    String genre;
    String description;
    AuthorDTO author;
}
