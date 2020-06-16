package learn.library.repository.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;

import java.util.List;

public interface BookDao {

    List<Book> getBooks();

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByTitle(String title);

    void deleteBookById(long id);

    long addBook(Book book);
}
