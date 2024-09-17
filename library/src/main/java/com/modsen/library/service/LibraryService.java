package com.modsen.library.service;

import com.modsen.library.dto.BookResponse;

import java.util.List;

public interface LibraryService extends AbstractService<Long> {
    List<BookResponse> findAvailableBooks();

    void changeAvailability(Long id);

    void setToken(String token);
}
