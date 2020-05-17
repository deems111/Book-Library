package testDao;

import configuration.TestConfig;
import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.repository.BookDaoImpl;
import learn.library.repository.CommentDaoImpl;
import net.bytebuddy.build.ToStringPlugin;
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
@Import(value = {CommentDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class TestCommentDao {

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private TestEntityManager testEntityManager;

    private String TEST_AUTHOR_NAME = "Test_author_name";
    private String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private String TEST_GENRE_NAME = "Test genre name";
    private String TEST_BOOK_TITLE = "Test book title";
    private String TEST_COMMENT_AUTHOR_NAME = "Test Name";
    private String TEST_COMMENT_AUTHOR_SUBJECT = "Test subject";

    private Author author = new Author(null, new Book(), TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME);
    private Genre genre = new Genre(TEST_GENRE_NAME);

    @Test
    private void addComment() {
        Book book = addBook();
        Comment comment = new Comment(TEST_COMMENT_AUTHOR_NAME, TEST_COMMENT_AUTHOR_SUBJECT, book);
        commentDao.addComment(comment);

        Assert.isTrue(commentDao.getCommentsByBookId(book.getId()).size() == 1, "Add comment size is not 1");
    }

    @Test
    public void deleteComment() {
        Book book = addBook();
        Comment comment = new Comment(TEST_COMMENT_AUTHOR_NAME, TEST_COMMENT_AUTHOR_SUBJECT, book);
        long id = commentDao.addComment(comment);
        commentDao.deleteComment(id);

        Assert.isTrue(commentDao.getComment(id) == null, "Deleted comment is not null");

    }

@Test
    public void deleteCommentsToBook() {
    Book book = addBook();
    Comment comment = new Comment(TEST_COMMENT_AUTHOR_NAME, TEST_COMMENT_AUTHOR_SUBJECT, book);
    long id = commentDao.addComment(comment);
    commentDao.deleteCommentsToBook(book.getId());

    Assert.isTrue(commentDao.getCommentsByBookId(book.getId()).isEmpty(), "Deleted comment is not null");
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

    public Book addBook() {
        Book book = new Book(TEST_BOOK_TITLE, setGenre(), setAuthor(), null);
        for (Author author : book.getAuthors()) {
            author.setBook(book);
            testEntityManager.persist(author);
        }
        return testEntityManager.persist(book);
    }
}
