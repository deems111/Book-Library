package learn.library.repository.interfaces;

import learn.library.entity.Author;

public interface AuthorDao {

    long addAuthor(Author author);

    void deleteAuthor(long id);

    Author getAuthor(String surname, String name);

}
