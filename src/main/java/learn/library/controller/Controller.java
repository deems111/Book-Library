package learn.library.controller;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;
import learn.library.entity.dto.CommentDto;
import learn.library.entity.mappers.BookMapperImpl;
import learn.library.entity.mappers.CommentMapperImpl;
import learn.library.entity.mappers.interfaces.BookMapper;
import learn.library.entity.mappers.interfaces.CommentMapper;
import learn.library.repository.interfaces.BookRepository;
import learn.library.repository.interfaces.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" })
public class Controller {

    private final BookMapper bookMapper = new BookMapperImpl();
    private final CommentMapper commentMapper = new CommentMapperImpl();
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/books")
    public Flux<BookDto> getAllBooks() {
        Flux<Book> books = bookRepository.findAll();
        if (books != null) {
            return books.map(bookMapper::toDto);
        }
        return null;
    }

    @PostMapping("/books")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookMapper.toEntity(bookDto)).map(bookMapper::toDto);
    }

    @GetMapping("/books/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @PutMapping("/books/{id}")
    public Mono<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookMapper.toEntity(bookDto)).map(bookMapper::toDto);
    }

    @DeleteMapping("/books/{id}")
    public Mono<Void> deleteBook(@PathVariable(name = "id") String id) {
        return Mono.zip(
                bookRepository.deleteById(id),
                commentRepository.deleteByBookId(id),
                (b, c) -> null
        );
    }

    @GetMapping("/comments/{id}")
    public Flux<CommentDto> showBookComments(@PathVariable("id") String bookId) {
        return commentRepository.findCommentsByBook(bookRepository.findById(bookId)).map(commentMapper::toDto);
    }

}