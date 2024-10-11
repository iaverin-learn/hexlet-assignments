package exercise.controller;

import java.util.List;
import java.util.NoSuchElementException;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @PostMapping("")
    public ResponseEntity<BookDTO> create(@RequestBody BookCreateDTO bookCreateDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookCreateDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("")
    public List<BookDTO> index() {
        return bookService.index();
    }

    @GetMapping("/{id}")
    public BookDTO show(@PathVariable long id) {
        return bookService.show(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        try {
            return ResponseEntity.ok().body(bookService.update(id, bookUpdateDTO));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // END
}
