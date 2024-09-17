package com.modsen.book.service;

import com.modsen.book.dto.BookRequest;
import com.modsen.book.dto.BookResponse;

public interface BookService extends AbstractService<Long, BookRequest, BookResponse> {
    BookResponse findByIsbn(String isbn);

    void setToken(String token);
}
