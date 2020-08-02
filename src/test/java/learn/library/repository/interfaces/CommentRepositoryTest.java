package learn.library.repository.interfaces;

import Configuration.Config;
import com.github.cloudyrock.mongock.SpringMongock;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(Config.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    private static final String SUBJECT = "TestSubject";
    private static final String NAME = "TestName";
    private static final String TEST_AUTHOR = "Test_author_name_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    private Book testBook;

    @BeforeEach
    void init() {
        mongock.execute();
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
        Comment comment = mongoTemplate.save(new Comment(NAME, SUBJECT, testBook));
        commentRepository.deleteById(comment.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(testBook));
        Assert.isTrue(mongoTemplate.find(query, Comment.class).size() == 0, "Get book by title is not null");
    }

    @Test
    public void getCommentsByBookId() {
        createBook();
        Comment comment = mongoTemplate.save(new Comment(NAME, SUBJECT, testBook));
        Comment anotherComment = mongoTemplate.save(new Comment(NAME+ "another", SUBJECT + "another", testBook));

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

        this.testBook = mongoTemplate.save(book);
    }

}