package com.modsen.book.service.impl;

import com.modsen.book.dto.AuthorDTO;
import com.modsen.book.dto.BookRequest;
import com.modsen.book.dto.BookResponse;
import com.modsen.book.dto.WebClientRequest;
import com.modsen.book.exception.NotCreatedException;
import com.modsen.book.exception.NotFoundException;
import com.modsen.book.exception.WebClientException;
import com.modsen.book.model.Author;
import com.modsen.book.model.Book;
import com.modsen.book.repository.AuthorRepository;
import com.modsen.book.repository.BookRepository;
import com.modsen.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private static final String LIBRARY_MODULE_URI = "http://localhost:8080/api/library";
    private final WebClient webClient;
    private String token;
    public void setToken(String token) {
        this.token = token;
    }

    private BookResponse toDTO(Book book) {
        return modelMapper.map(book, BookResponse.class);
    }
    private Book toBook(BookRequest dto) {
        return modelMapper.map(dto, Book.class);
    }


    @Override
    @Transactional
    public void add(BookRequest dto) {
        Book book = toBook(dto);
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new NotCreatedException("Book with isbn " + dto.getIsbn() + " already exists");
        }

        AuthorDTO authorDTO = dto.getAuthor();
        Author author = authorRepository
                .findByNameAndSurnameAndPatronymic(authorDTO.getName(), authorDTO.getSurname(), authorDTO.getPatronymic())
                .orElseGet(() -> createAuthor(authorDTO));
        book.setAuthor(author);
        bookRepository.save(book);
        sendCreationRequest(book.getId());
    }

    private Author createAuthor(AuthorDTO authorDTO) {
        Author newAuthor = modelMapper.map(authorDTO, Author.class);
        return authorRepository.save(newAuthor);
    }

    private void sendCreationRequest(Long id) {
        webClient.post()
                .uri(LIBRARY_MODULE_URI)
                //.contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new WebClientRequest(id))
                .header("Authorization", token)
                .retrieve()
                .toBodilessEntity()
                .doOnError(throwable -> {
                    throw new WebClientException(throwable.getMessage());
                })
                .block();
    }

    private void sendDeleteRequest(Long id) {
        webClient.delete()
                .uri(LIBRARY_MODULE_URI + "/" + id)
                .header("Authorization", token)
                .retrieve()
                .toBodilessEntity()
                .doOnError(throwable -> {
                    throw new WebClientException(throwable.getMessage());
                })
                .block();
    }


    @Override
    public BookResponse findById(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        Book book = bookRepository.findById(id).get();
        return toDTO(book);
    }

    @Override
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void update(BookRequest dto, Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        Book book = toBook(dto);
        book.setId(id);
        Author author = authorRepository.save(modelMapper.map(dto.getAuthor(), Author.class));
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
        sendDeleteRequest(id);
    }


    @Override
    public BookResponse findByIsbn(String isbn) {
        if (bookRepository.findByIsbn(isbn).isEmpty()) {
            throw new NotFoundException("Book with isbn " + isbn + " not found");
        }
        return toDTO(bookRepository.findByIsbn(isbn).get());
    }

}
