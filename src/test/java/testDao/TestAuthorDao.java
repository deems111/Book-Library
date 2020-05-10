package testDao;

import configuration.TestConfig;
import learn.library.entity.Author;
import learn.library.repository.AuthorDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({AuthorDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class TestAuthorDao {

    @Autowired
    private AuthorDaoImpl authorDao;

    private String TEST_AUTHOR_NAME = "Test_author_name";
    private String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private long TEST_DEFAULT_AUTHOR_BOOK_ID = 2L; //book with id =2 must exist

    private Author testAuthor = new Author();

    private void setAuthor() {
        testAuthor.setName(TEST_AUTHOR_NAME);
        testAuthor.setSurname(TEST_AUTHOR_SURNAME);
        testAuthor.setBookId(TEST_DEFAULT_AUTHOR_BOOK_ID);
    }

    @Test
    public void addAuthor() {
        setAuthor();
        authorDao.addAuthor(testAuthor);

        Assert.notNull(authorDao.getAuthor(testAuthor), "Get author return null");
    }

}
