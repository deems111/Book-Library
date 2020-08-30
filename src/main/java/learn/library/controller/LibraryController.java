package learn.library.controller;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.dto.BookDto;
import learn.library.entity.dto.CommentDto;
import learn.library.service.LibraryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000" })
public class LibraryController {

    private final LibraryImpl libraryService;

    @Autowired
    public LibraryController(LibraryImpl libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/books")
    public List<BookDto> getAllCourses() {
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = libraryService.getBooks();

        for (Book book : books) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }

    @GetMapping("/view/{id}")
    public BookDto getTutorialById(@PathVariable("id") long id) {
        Book book = libraryService.getBooksById(id);

        return new BookDto(book);
    }

    @PostMapping("/create")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        try {
            libraryService.addBookDto(bookDto.getAuthors(), bookDto.getTitle(), bookDto.getGenre());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBook(@RequestBody BookDto bookDto) {
        try {
            libraryService.updateBook(bookDto.getId(), bookDto.getAuthors(), bookDto.getTitle(), bookDto.getGenre());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable(name = "id") Long id) {
        if (libraryService.getBooksById(id) != null) {
            libraryService.deleteBookById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/comments/{id}")
    public List<CommentDto> showBookComments(@PathVariable("id") long id) {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = libraryService.getCommentsByBookId(id);
        for (Comment comment : comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return commentDtos;
    }

}
