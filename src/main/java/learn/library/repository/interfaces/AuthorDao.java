package learn.library.repository.interfaces;

import learn.library.entity.Author;

import java.util.Set;

public interface AuthorDao {

    long addAuthor(Author author);

    Author getAuthor(Author author);

    Set<Author> getAuthorsByBookId(long bookId);

    void deleteAuthor(long bookId);

}
