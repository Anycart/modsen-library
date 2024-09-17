package com.modsen.library.controller;

import com.modsen.library.dto.BookResponse;
import com.modsen.library.dto.WebClientRequest;
import com.modsen.library.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BearerAuth", scheme = "bearer")
@RequestMapping("/api/library")
@Tag(name = "Controller for accounting of free and occupied books")
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping
    @Operation(summary = "Add a new book record")
    public ResponseEntity<String> createLibraryEntry(@RequestBody WebClientRequest request) {
        libraryService.add(request.getId());
        return ResponseEntity.ok("Added book with id " + request.getId());
    }

    @GetMapping("/available")
    @Operation(
            summary = "Getting information about available books"
    )
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<BookResponse>> availableBooks(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token
    ) {
        libraryService.setToken(token);
        return ResponseEntity.ok(libraryService.findAvailableBooks());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Changing the availability of a book by id",
            description = "Depending on the availability of the book, the values " +
                    "of the fields with the borrowing and returning dates are either set or reset."
    )
    public ResponseEntity<String> changeAvailability(@PathVariable Long id) {
        libraryService.changeAvailability(id);
        return ResponseEntity.ok("Book status with id " + id + " updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete record by id")
    public ResponseEntity<String> deleteLibraryEntry(@PathVariable Long id) {
        libraryService.delete(id);
        return ResponseEntity.ok("Book deleted with id " + id);
    }

}
