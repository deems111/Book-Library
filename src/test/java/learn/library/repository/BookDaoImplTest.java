package learn.library.repository;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(value = {AuthorDaoImpl.class, GenreDaoImpl.class, BookDaoImpl.class, CommentDaoImpl.class})
class BookDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;
    @Autowired
    private GenreDaoImpl genreDao;
    @Autowired
    private BookDaoImpl bookDao;
    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private TestEntityManager testEntityManager;

    private static String TEST_AUTHOR_NAME = "Test_author_name";
    private static String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private static String TEST_GENRE_NAME = "Test genre name";
    private static String TEST_BOOK_TITLE = "Test book title";

    private Author author = new Author(null, TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME);
    private Genre genre = new Genre(TEST_GENRE_NAME);

    @Test
    public void addBook() {
        Book book = new Book(TEST_BOOK_TITLE, setGenre(), setAuthor());
        for (Author author : book.getAuthors()) {
            testEntityManager.persist(author);
        }
        bookDao.addBook(book);

        Assert.isTrue(bookDao.getBooksByAuthor(author).size() == 1, "Get book by author is null");
        Assert.isTrue(bookDao.getBooksByTitle(TEST_BOOK_TITLE).size() == 1, "Get book by title is null");
    }

    @Test
    public void deleteBook() {
        Book book = new Book(TEST_BOOK_TITLE, setGenre(), setAuthor());
        for (Author author : book.getAuthors()) {
            testEntityManager.persist(author);
        }
        long id = testEntityManager.persist(book).getId();
        bookDao.deleteBookById(id);

        Assert.isTrue(bookDao.getBooksById(id) == null, "Delete book by author is not null");
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