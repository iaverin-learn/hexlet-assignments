package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    public AuthorDTO create(AuthorCreateDTO authorDTO) {
        var author = authorMapper.map(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.map(author);
    }

    public List<AuthorDTO> index() {
        return authorRepository.findAll().stream().map(authorMapper::map).collect(Collectors.toList());
    }

    public AuthorDTO showById(long id) {
        var author = authorRepository.findById(id).get();
        return authorMapper.map(author);
    }

    public AuthorDTO update(long id, AuthorUpdateDTO authorUpdateDTO) {
        var author = authorRepository.findById(id).get();
        authorMapper.update(authorUpdateDTO, author);
        author = authorRepository.save(author);
        return authorMapper.map(author);
    }

    // END
}
