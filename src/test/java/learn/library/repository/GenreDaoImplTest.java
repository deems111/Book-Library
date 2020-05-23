package learn.library.repository;

import learn.library.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

@JdbcTest
@Import({GenreDaoImpl.class})
class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl genreDao;

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
        long genreId = genreDao.addGenre(testGenre);
        genreDao.deleteGenre(genreId);

        Assert.isNull(genreDao.getGenre(TEST_GENRE_NAME), "Genre delete is not OK");
    }

}