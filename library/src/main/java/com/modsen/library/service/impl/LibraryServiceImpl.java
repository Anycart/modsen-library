package com.modsen.library.service.impl;

import com.modsen.library.dto.BookResponse;
import com.modsen.library.exception.NotCreatedException;
import com.modsen.library.exception.NotFoundException;
import com.modsen.library.exception.WebClientException;
import com.modsen.library.model.LibraryRecord;
import com.modsen.library.repository.LibraryRepository;
import com.modsen.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private static final String BOOK_MODULE_URI = "http://localhost:8080/api/books";
    private final WebClient webClient;
    private String token;
    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void add(Long id) {
        if (libraryRepository.findById(id).isPresent()) {
            throw new NotCreatedException("Book with id " + id + " already exists");
        }
        libraryRepository.save(new LibraryRecord(id));
    }

    @Override
    public List<BookResponse> findAvailableBooks() {
        List<Long> availableBooksIds = libraryRepository.findAllByIssueDateIsNull().
                stream().
                map(LibraryRecord::getId).
                toList();
        List<BookResponse> availableBooks = new ArrayList<>();
        availableBooksIds.forEach(id -> {
            BookResponse book = webClient.get()
                    .uri(BOOK_MODULE_URI + "/" + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(BookResponse.class)
                    .doOnError(throwable -> {
                        throw new WebClientException(throwable.getMessage());
                    })
                    .block();
            availableBooks.add(book);
        });
        return availableBooks;
    }

    @Override
    public void changeAvailability(Long id) {
        if (libraryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Book with id " + id + " absent");
        }
        LibraryRecord library = libraryRepository.findById(id).get();
        LocalDateTime date = library.getIssueDate();
        if (date != null) {
            library.setIssueDate(null);
            library.setAcceptanceDate(null);
        } else {
            library.setIssueDate(LocalDateTime.now());
            library.setAcceptanceDate(LocalDateTime.now().plusMonths(1));
        }
        libraryRepository.save(library);
    }

    @Override
    public void delete(Long id) {
        if (libraryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Book with id " + id + " absent");
        }
        libraryRepository.deleteById(id);
    }

}
