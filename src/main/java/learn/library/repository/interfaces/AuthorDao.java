package learn.library.repository.interfaces;

import learn.library.entity.Author;

import java.util.List;

public interface AuthorDao {

    long addAuthor(Author author);

    Author getAuthor(Author author);

    List<Author> getAuthorsByBookId(long bookId);

    void deleteAuthor(long bookId);

}
