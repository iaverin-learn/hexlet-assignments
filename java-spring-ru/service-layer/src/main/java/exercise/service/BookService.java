package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookService {
    // BEGIN
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMapper bookMapper; 

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var author = authorRepository.findById(bookCreateDTO.getAuthorId()).get();
        var book = bookMapper.map(bookCreateDTO);
        book.setAuthor(author);
        return bookMapper.get(bookRepository.save(book));
    }

    public List<BookDTO> index() {
            return bookRepository.findAll().stream().map(bookMapper::get).collect(Collectors.toList());
    }

    public BookDTO show(long id) {
        var book = bookRepository.findById(id).get();
        book.getAuthor();
        return bookMapper.get(book);
    }

    public BookDTO update(long id, BookUpdateDTO bookUpdateDTO) {
        var book = bookRepository.findById(id).get();
        bookMapper.update(bookUpdateDTO, book);
        
        if (bookUpdateDTO.getAuthorId() != null ) {
            var author = book.getAuthor();
            book.setAuthor(author);
        }
        return bookMapper.get(bookRepository.save(book));
    }
    // END
}
