package com.modsen.library.repository;

import com.modsen.library.model.LibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryRecord, Long> {
    List<LibraryRecord> findAllByIssueDateIsNull();
}
