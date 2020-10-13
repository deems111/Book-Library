package learn.library.repository.interfaces;

import configuration.Config;
import com.github.cloudyrock.mongock.SpringMongock;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
@Import(Config.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    private static final String TEST_AUTHOR = "Test_author_name_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    @Order(1)
    public void addBook() {

        int count = mongoTemplate.findAll(Book.class).size();
        bookRepository.save(createBook());

        Assert.isTrue(mongoTemplate.findAll(Book.class).size() == count + 1, "Get books <> 1");
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(TEST_BOOK_TITLE));
        Assert.isTrue(mongoTemplate.find(query, Book.class).size() == 1, "Get book by title <> 1");
        query = new Query();
        query.addCriteria(Criteria.where("genre").is(TEST_GENRE_NAME));
        Assert.isTrue(mongoTemplate.find(query, Book.class).size()  == 1, "Get book by genre <> 1");
    }

    @Test
    @Order(3)
    public void deleteBook() {

        Book book = mongoTemplate.save(createBook());

        int sizeBeforeDelete = mongoTemplate.findAll(Book.class).size();
        bookRepository.deleteById(book.getId());
        int sizeAfterDelete = mongoTemplate.findAll(Book.class).size();

        Assert.isTrue(sizeBeforeDelete - sizeAfterDelete == 1, "Size difference is not 1");
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is("Test book title to delete"));
        Assert.isTrue(mongoTemplate.find(query, Book.class).size() == 0, "Get book by title is not null");
        query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        Assert.isTrue(mongoTemplate.find(query, Comment.class).size() == 0, "Get book by title is not null");
    }

    @Test
    @Order(2)
    public void getBookByAuthorTest() throws Exception {


        List<Book> books = mongoTemplate.findAll(Book.class);

        Assert.isTrue(bookRepository.findByAuthors(TEST_AUTHOR).size() == 1, "Get books size is not 1");
        Assert.isTrue(bookRepository.findByAuthors(TEST_AUTHOR).get(0)
                .getAuthors().iterator().next().equalsIgnoreCase(TEST_AUTHOR), "Get books size is not 1");
    }

    private Book createBook() {
        List<String> authors = new ArrayList<>();
        authors.add(TEST_AUTHOR);
        Book book = new Book(TEST_BOOK_TITLE, TEST_GENRE_NAME, authors);

        return book;
    }

}