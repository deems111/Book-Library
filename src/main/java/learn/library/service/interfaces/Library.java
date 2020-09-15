package learn.library.service.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.entity.dto.BookDto;
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

    Book updateBook(BookDto bookDto);

    Genre addGenre(Genre genre);

    Book addBook(Book book);

    Book addBook(BookDto bookDto);

    Genre getGenre(String genre);

    Comment addComment(Comment comment);

    void deleteComment(long id);

    void deleteCommentByBookId(Long bookId);

    List<Comment> getCommentsByBookId(long bookId);


    @Transactional()
    Genre setGenreForAddBook(String genre);
}


