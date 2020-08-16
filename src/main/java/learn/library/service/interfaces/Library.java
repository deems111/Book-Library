package learn.library.service.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface for library service
 */
public interface Library {

    List<Book> getBooks();

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByTitle(String title);

    Book getBooksById(long id);

    void deleteBookById(long id);

    Book updateBook(Long id, String authors, String title, String genre);

    Genre addGenre(Genre genre);

    Book addBook(Book book);

    Genre getGenre(String genre);

    Comment addComment(Comment comment);

    void deleteComment(long id);

    void deleteCommentByBookId(Long bookId);

    List<Comment> getCommentsByBookId(long bookId);


    @Transactional()
    Genre setGenreForAddBook(String genre);
}


