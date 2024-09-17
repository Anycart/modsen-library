package com.modsen.book.service.impl;

import com.modsen.book.dto.AuthorDTO;
import com.modsen.book.exception.NotCreatedException;
import com.modsen.book.exception.NotFoundException;
import com.modsen.book.model.Author;
import com.modsen.book.repository.AuthorRepository;
import com.modsen.book.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    private AuthorDTO toDTO(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }
    private Author toAuthor(AuthorDTO dto) {
        return modelMapper.map(dto, Author.class);
    }

    @Override
    public void add(AuthorDTO dto) {
        Author author = toAuthor(dto);
        if (authorRepository.findByNameAndSurnameAndPatronymic(author.getName(),
                author.getSurname(), author.getPatronymic()).isPresent()) {
            throw new NotCreatedException("An author with this full name already exists");
        }
        authorRepository.save(author);
    }

    @Override
    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AuthorDTO findById(Long id) {
        if (authorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Author with id " + id + " not found");
        }
        Author author = authorRepository.findById(id).get();
        return toDTO(author);
    }

    @Override
    public void update(AuthorDTO dto, Long id) {
        if (authorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Author with id " + id + " not found");
        }
        Author author = toAuthor(dto);
        author.setId(id);
        authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        if (authorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Author with id " + id + " not found");
        }
        authorRepository.deleteById(id);
    }
}
