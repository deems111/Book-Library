package learn.library.repository;

import learn.library.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

@JdbcTest
@Import({AuthorDaoImpl.class})
class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    private static final String TEST_AUTHOR_NAME = "Test_author_name";
    private static final String TEST_AUTHOR_SURNAME = "Test_author_surname";
    private static final long TEST_DEFAULT_AUTHOR_ID = 2L; //book with id =2 must exist

    private Author testAuthor = new Author();

    private void setAuthor() {
        testAuthor.setName(TEST_AUTHOR_NAME);
        testAuthor.setSurname(TEST_AUTHOR_SURNAME);
    }

    @Test
    public void addAuthor() {
        setAuthor();
        authorDao.addAuthor(testAuthor);

        Assert.notNull(authorDao.getAuthor(TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME), "Add author return null");
    }

    @Test
    public void deleteAuthor() {
        setAuthor();
        authorDao.deleteAuthor(authorDao.addAuthor(testAuthor));

        Assert.isTrue(authorDao.getAuthor(TEST_AUTHOR_SURNAME, TEST_AUTHOR_NAME) == null, "Deleted author is not null");
    }


}