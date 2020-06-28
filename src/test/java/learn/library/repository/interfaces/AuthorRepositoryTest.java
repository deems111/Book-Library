package learn.library.repository.interfaces;

import learn.library.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private static final String AUTHOR_NAME = "TestName";
    private static final String AUTHOR_SURNAME = "TestSurname";
    private static final Long TEST_ID = 1L;

    @Test
    public void addAuthor() {
        Author author = authorRepository.save(new Author(null, AUTHOR_SURNAME, AUTHOR_NAME));
        assertThat(author).isNotNull();
        assertThat(author.getId()).isNotNull();

        Author authorTem = testEntityManager.find(Author.class, author.getId());
        assertThat(author).isNotNull();
        assertThat(author.equals(authorTem));
        assertThat(authorTem.getSurname().equalsIgnoreCase(AUTHOR_SURNAME));
        assertThat(authorTem.getName().equalsIgnoreCase(AUTHOR_NAME));
    }

    @Test
    public void deleteAuthorById() {
        Author author = testEntityManager.find(Author.class, TEST_ID);
        assertThat(author).isNotNull();
        testEntityManager.detach(author);

        authorRepository.deleteById(TEST_ID);
        Author authorTem = testEntityManager.find(Author.class, TEST_ID);
        assertThat(authorTem).isNull();
    }

    @Test
    public void deleteAuthor() {
        Author author = testEntityManager.find(Author.class, TEST_ID);
        assertThat(author).isNotNull();
        testEntityManager.detach(author);

        authorRepository.delete(new Author(TEST_ID, AUTHOR_SURNAME, AUTHOR_NAME));
        Author authorTem = testEntityManager.find(Author.class, TEST_ID);
        assertThat(authorTem).isNull();
    }

    @Test
    public void findAuthorByNameAndSurname() {
        Author author = authorRepository.save(new Author(null, AUTHOR_SURNAME, AUTHOR_NAME));

        Author authorTem = authorRepository.findAuthorByNameAndSurname(AUTHOR_NAME, AUTHOR_SURNAME);
        assertThat(authorTem).isNotNull();
        assertThat(authorTem.getSurname().equalsIgnoreCase(AUTHOR_SURNAME));
        assertThat(authorTem.getName().equalsIgnoreCase(AUTHOR_NAME));
    }

}