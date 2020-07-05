package learn.library.service.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;

import java.util.List;

/**
 * Interface for library service
 */
public interface Library {

    List<Book> getBooks();

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByTitle(String title);

    Book getBooksById(String id);

    void deleteBookById(String id);

    Book addBook(Book book);

    Comment addComment(Comment comment);

    void deleteComment(String id);

    void deleteCommentByBookId(String bookId);

    List<Comment> getCommentsByBookId(String bookId);

}


