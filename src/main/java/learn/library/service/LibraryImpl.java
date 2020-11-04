package learn.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.repository.interfaces.BookRepository;
import learn.library.repository.interfaces.CommentRepository;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class LibraryImpl implements Library {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "getAllBooksGroup", commandKey = "getAllBooksCommand", fallbackMethod = "getAllBooksFallback")
    public List<Book> getBooks() throws Exception {
        if (Math.round(Math.random()) == 0) {
            log.info("Hystrix FallBack");
            throw new Exception("Hystrix FallBack Exception");
        }
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksFallback() {
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBooksById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            commentRepository.deleteAllByBook(optionalBook.get());
        }
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteCommentByBookId(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return;
        }
        commentRepository.deleteAllByBook(optionalBook.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return null;
        }
        return commentRepository.findCommentsByBook(optionalBook.get());
    }

    @Override
    public Book updateBook(Book book) {
        bookRepository.delete(book);
        return bookRepository.save(book);
    }

}
