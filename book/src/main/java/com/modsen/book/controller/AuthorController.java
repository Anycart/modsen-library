package com.modsen.book.controller;

import com.modsen.book.dto.AuthorDTO;
import com.modsen.book.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
@Tag(name = "Controller for operations with authors ")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @Operation(
            summary = "Create new author"
    )
    public ResponseEntity<String> createAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.add(authorDTO);
        return ResponseEntity.ok("New author created: " + authorDTO.getName() + " " + authorDTO.getSurname());
    }

    @Operation(
            summary = "Getting all authors"
    )
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<AuthorDTO> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get author information by id"
    )
    public ResponseEntity<AuthorDTO> authorInfo(@PathVariable Long id) {
        AuthorDTO author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updating information about author by id",
            description = "The request body contains the id of the author being changed and the author's dto with the updated fields."
    )
    public ResponseEntity<String> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        authorService.update(authorDTO, id);
        return ResponseEntity.ok("Author updated with id " + id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete author by id"
    )
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok("Deleted author with id " + id);
    }

}
