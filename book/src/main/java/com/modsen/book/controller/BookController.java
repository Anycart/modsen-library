package com.modsen.book.controller;

import com.modsen.book.dto.BookRequest;
import com.modsen.book.dto.BookResponse;
import com.modsen.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BearerAuth", scheme = "bearer")
@RequestMapping("/api/books")
@Tag(name = "Controller for operations with books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    @Operation(
            summary = "Create book"
    )
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<String> createBook(
            @RequestBody BookRequest bookDTO,
            @Parameter(hidden = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
    ) {
        bookService.setToken(token);
        bookService.add(bookDTO);
        return ResponseEntity.ok("Book created: " + bookDTO.getName());
    }

    @GetMapping
    @Operation(
            summary = "Get information about all books"
    )
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get information about book by id"
    )
    public ResponseEntity<BookResponse> bookInfo(@PathVariable Long id) {
        BookResponse book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(
            summary = "Get information about book by isbn"
    )
    public ResponseEntity<BookResponse> findByIsbn(@PathVariable String isbn) {
        BookResponse book = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update information about book by id",
            description = "The request body contains the id of the book being changed " +
                    "and the dto of the book with the changed information."
    )
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        bookService.update(bookRequest, id);
        return ResponseEntity.ok("Book updated with id " + id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete book by id"
    )
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id,
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token
    ) {
        bookService.setToken(token);
        bookService.delete(id);
        return ResponseEntity.ok("Book deleted with id " + id);
    }

}
