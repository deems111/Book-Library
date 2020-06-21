package learn.library.repository;

import learn.library.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({GenreDaoImpl.class})
public class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final String TEST_GENRE_NAME = "Test genre name";
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