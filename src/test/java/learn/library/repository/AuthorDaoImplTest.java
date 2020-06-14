package learn.library.repository;

import configuration.TestConfig;
import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({AuthorDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
class AuthorDaoImplTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AuthorDaoImpl authorDao;

    private static final String TEST_AUTHOR_NAME = "Test_author_name";
    private static final String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    private Author author = new Author(null, TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME);
    private Genre genre = new Genre(TEST_GENRE_NAME);

    @Test
    public void addAuthor() {
        Book book = new Book(TEST_BOOK_TITLE, setGenre(), setAuthor());
        for (Author author : book.getAuthors()) {
            authorDao.addAuthor(author);
        }
        testEntityManager.persist(book);

        Assert.isTrue(authorDao.getAuthor(author).getSurname().equalsIgnoreCase(TEST_AUTHOR_SURNAME), "Surname of author is not equal");
        Assert.isTrue(authorDao.getAuthor(author).getName().equalsIgnoreCase(TEST_AUTHOR_NAME), "Name of author is not equal");
    }

    private Set<Author> setAuthor() {
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        return authors;
    }

    private Genre setGenre() {
        testEntityManager.persist(genre);
        return genre;
    }

}