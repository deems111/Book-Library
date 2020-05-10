package learn.library.service.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;

import java.util.List;

/**
 * Interface for library service
 */
public interface Library {

    List<Book> getBooks();

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByTitle(String title);

    void deleteBookById(long id);

    long addGenre(Genre genre);

    long addBook(Book book);

    Genre getGenre(String genre);
}


