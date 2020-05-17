package testDao;

import configuration.TestConfig;
import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import learn.library.repository.AuthorDaoImpl;
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

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({AuthorDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class TestAuthorDao {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AuthorDaoImpl authorDao;

    private String TEST_AUTHOR_NAME = "Test_author_name";
    private String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private String TEST_GENRE_NAME = "Test genre name";
    private String TEST_BOOK_TITLE = "Test book title";

    private Author author = new Author(null, new Book(), TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME);
    private Genre genre = new Genre(TEST_GENRE_NAME);

    @Test
    public void addAuthor() {
        Book book = new Book(TEST_BOOK_TITLE, setGenre(), setAuthor(), null);
        for (Author author : book.getAuthors()) {
            author.setBook(book);
            authorDao.addAuthor(author);
        }
        testEntityManager.persist(book);

        Assert.isTrue(authorDao.getAuthor(author).getSurname().equalsIgnoreCase(TEST_AUTHOR_SURNAME), "Surname of author is not equal");
        Assert.isTrue(authorDao.getAuthorsByBookId(book.getId()).size() == 1, "Author's size is not 1");

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
