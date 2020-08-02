package learn.library.service;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.repository.interfaces.*;
import learn.library.service.interfaces.Library;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class LibraryImpl implements Library {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public LibraryImpl(BookRepository bookRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthors(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
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
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
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
            return new ArrayList<>();
        }
        return commentRepository.findCommentsByBook(optionalBook.get());
    }

}
