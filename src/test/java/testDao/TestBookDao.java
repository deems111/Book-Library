package testDao;

import configuration.TestConfig;
import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import learn.library.repository.AuthorDaoImpl;
import learn.library.repository.BookDaoImpl;
import learn.library.repository.GenreDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuthorDaoImpl.class, GenreDaoImpl.class, BookDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class TestBookDao {

    @Autowired
    private AuthorDaoImpl authorDao;
    @Autowired
    private GenreDaoImpl genreDao;
    @Autowired
    private BookDaoImpl bookDao;

    private static final String TEST_AUTHOR_NAME = "Test_author_name";
    private static final String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private static final long TEST_DEFAULT_AUTHOR_BOOK_ID = -1L;
    private static final long TEST_DEFAULT_BOOK_ID = -1L;
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    private Author testAuthor = new Author();
    private Genre testGenre = new Genre();
    private Book testBook = new Book();

    @Test
    public void addBook() {
        prepareBook();
        long bookId = bookDao.addBook(testBook);

        Assert.isTrue(bookDao.getBooksByAuthor(testAuthor).size() == 1, "Get book by author is null");
        Assert.isTrue(bookDao.getBooksByTitle(TEST_BOOK_TITLE).size() == 1, "Get book by title is null");
    }

    @Test
    public void deleteBook() {
        prepareBook();
        long id = bookDao.addBook(testBook);
        bookDao.deleteBookById(id);

        Assert.isTrue(bookDao.getBooksByAuthor(testAuthor).isEmpty(), "Delete book by author is not null");
        Assert.isTrue(bookDao.getBooksByTitle(TEST_BOOK_TITLE).isEmpty(), "Delete book by title is not null");
    }

    private void setAuthor() {
        testAuthor.setName(TEST_AUTHOR_NAME);
        testAuthor.setSurname(TEST_AUTHOR_SURNAME);
    }

    private void setBook() {
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthor);
        testBook.setAuthors(authors);
        testBook.setGenre(testGenre);
        testBook.setName(TEST_BOOK_TITLE);
        testBook.setId(TEST_DEFAULT_BOOK_ID);
    }

    private void prepareBook() {
        if (genreDao.getGenre(TEST_GENRE_NAME) == null) {
            testGenre.setName(TEST_GENRE_NAME);
            testGenre.setId(genreDao.addGenre(testGenre));
        } else {
            testGenre = genreDao.getGenre(TEST_GENRE_NAME);
        }
        setAuthor();
        setBook();
    }

}
