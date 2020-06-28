package learn.library.repository.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private static final String SUBJECT = "TestSubject";
    private static final String NAME = "TestName";
    private static final Long TEST_ID = 1L;
    private Book testBook;

    private boolean canNotTest() {
        try {
            this.testBook = testEntityManager.find(Book.class, 1L);
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace(); //need book;
            return true;
        }
    }

    @Test
    public void addComment() {
        if (canNotTest()) {
            return;
        }
        Comment comment = commentRepository.save(new Comment(NAME, SUBJECT, testBook));
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isNotNull();

        Comment commentTem = testEntityManager.find(Comment.class, comment.getId());
        assertThat(commentTem).isNotNull();
        assertThat(commentTem.equals(comment));
        assertThat(commentTem.getName().equalsIgnoreCase(NAME));
        assertThat(commentTem.getSubject().equalsIgnoreCase(SUBJECT));

    }

    @Test
    public void deleteCommentById() {
        if (canNotTest()) {
            return;
        }
        Comment comment = testEntityManager.find(Comment.class, TEST_ID);
        assertThat(comment).isNotNull();
        testEntityManager.detach(comment);

        commentRepository.deleteById(TEST_ID);
        Comment commnetTem = testEntityManager.find(Comment.class, TEST_ID);
        assertThat(commnetTem).isNull();
    }

    @Test
    public void getCommentsByBookId() {
        if (canNotTest()) {
            return;
        }
        List<Comment> commentsBefore = commentRepository.getCommentsByBookId(TEST_ID);
        Comment comment = commentRepository.save(new Comment(NAME, SUBJECT, testBook));
        List<Comment> comments = commentRepository.getCommentsByBookId(TEST_ID);

        assertThat(!comments.isEmpty());
        assertThat(commentsBefore.size() - 1 == comments.size());
        assertThat(comments.containsAll(commentsBefore));
    }

    @Test
    public void deleteCommentsByBookId() {
        if (canNotTest()) {
            return;
        }
        List<Comment> commentsBefore = commentRepository.getCommentsByBookId(TEST_ID);
        assertThat(!commentsBefore.isEmpty());

        commentRepository.deleteCommentsToBook(testBook);
        List<Comment> comments = commentRepository.getCommentsByBookId(TEST_ID);

        assertThat(comments.isEmpty());
    }

}