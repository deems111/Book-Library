package learn.library.repository.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final String SUBJECT = "TestSubject";
    private static final String NAME = "TestName";
    private static final Long TEST_ID = 1L;
    private static final String TEST_AUTHOR = "Test_author_name_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    private Book testBook;

    @BeforeEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    public void addComment() {
        createBook();

        Comment comment = commentRepository.save(new Comment(NAME, SUBJECT, testBook));
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isNotNull();

        Comment commentTem = commentRepository.findCommentsByBook(testBook).get(0);
        assertThat(commentTem).isNotNull();
        assertThat(commentTem.equals(comment));
        assertThat(commentTem.getName().equalsIgnoreCase(NAME));
        assertThat(commentTem.getSubject().equalsIgnoreCase(SUBJECT));
    }

    @Test
    public void deleteCommentById() {
        createBook();
        Comment comment = commentRepository.save(new Comment(NAME, SUBJECT, testBook));

        commentRepository.deleteById(comment.getId());
        List<Comment> comments = commentRepository.findCommentsByBook(testBook);
        assertThat(comments).isEmpty();
    }

    @Test
    public void getCommentsByBookId() {
        createBook();
        Comment comment = commentRepository.save(new Comment(NAME, SUBJECT, testBook));
        Comment anotherComment = commentRepository.save(new Comment(NAME+ "another", SUBJECT + "another", testBook));

        List<Comment> comments = commentRepository.findCommentsByBook(testBook);

        assertThat(!comments.isEmpty());
        assertThat(comments.size()  == 2);
        assertThat(comments.contains(comment));
        assertThat(comments.contains(anotherComment));

    }

    private void createBook() {
        Set<String> authors = new HashSet<>();
        authors.add(TEST_AUTHOR);
        Book book = new Book(TEST_BOOK_TITLE, TEST_GENRE_NAME, authors);

        this.testBook = bookRepository.save(book);
    }

}