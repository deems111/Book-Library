package learn.library.controller;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;
import learn.library.entity.mappers.interfaces.BookMapper;
import learn.library.entity.mappers.interfaces.CommentMapper;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class BookLibraryController {

    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;
    private final Library libraryService;

    @GetMapping("/books")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllBooks() {
        try {
            return new ResponseEntity<>(
                    libraryService.getBooks().stream().map(bookMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/books")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        try {
            return new ResponseEntity<>(bookMapper.toDto(libraryService.addBook(bookMapper.toEntity(bookDto))), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") String id) {
        Book book = libraryService.getBooksById(id);
        try {
            return new ResponseEntity<>(bookMapper.toDto(book), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        try {
            return new ResponseEntity<>(bookMapper.toDto(libraryService.updateBook(bookMapper.toEntity(bookDto))), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteBook(@PathVariable(name = "id") String id) {
        try {
            libraryService.deleteBookById(id);
            libraryService.deleteCommentByBookId(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> showBookComments(@PathVariable("id") String bookId) {
        try {
            return new ResponseEntity<>(
                    libraryService.getCommentsByBookId(bookId).stream().map(commentMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}