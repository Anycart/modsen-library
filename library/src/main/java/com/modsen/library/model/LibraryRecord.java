package com.modsen.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LibraryRecord {
    @Id
    @Column(name = "id")
    Long id;
    @Column(name = "issueDate")
    LocalDateTime issueDate;
    @Column(name = "acceptanceDate")
    LocalDateTime acceptanceDate;

    public LibraryRecord(Long id) {
        this.id = id;
    }
}
