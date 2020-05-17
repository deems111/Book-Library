package testDao;

import configuration.TestConfig;
import learn.library.entity.Genre;
import learn.library.repository.GenreDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({GenreDaoImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class TestGenreDao {

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private TestEntityManager testEntityManager;

    private String TEST_GENRE_NAME = "Test genre name";
    private Genre testGenre = new Genre();

    @Test
    public void addGenre() {
        testGenre.setName(TEST_GENRE_NAME);
        genreDao.addGenre(testGenre);

        Assert.notNull(genreDao.getGenre(TEST_GENRE_NAME), "Get genre is not null");
    }

    @Test
    public void deleteGenre() {
        testGenre.setName(TEST_GENRE_NAME);
        genreDao.addGenre(testGenre);

        genreDao.deleteGenre(genreDao.getGenre(TEST_GENRE_NAME).getId());

        Assert.isNull(genreDao.getGenre(TEST_GENRE_NAME), "Genre delete is not OK");
    }

}
