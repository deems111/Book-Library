package learn.library.repository.interfaces;

import learn.library.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private static final String TITLE = "TestTitle";
    private static final Long TEST_ID = 1L;

    @Test
    public void addGenre() {
        Genre genre = genreRepository.save(new Genre(TITLE));
        assertThat(genre).isNotNull();
        assertThat(genre.getId()).isNotNull();

        Genre genreTem = testEntityManager.find(Genre.class, genre.getId());
        assertThat(genreTem).isNotNull();
        assertThat(genre.equals(genreTem));
        assertThat(genreTem.getName().equalsIgnoreCase(TITLE));
    }

    @Test
    public void deleteGenreById() {
        Genre genre = testEntityManager.find(Genre.class, TEST_ID);
        assertThat(genre).isNotNull();
        testEntityManager.detach(genre);

        genreRepository.deleteById(TEST_ID);
        Genre genreTem = testEntityManager.find(Genre.class, TEST_ID);
        assertThat(genreTem).isNull();
    }

    @Test
    public void getGenre () {
        Genre genreTem = testEntityManager.find(Genre.class, TEST_ID);
        assertThat(genreTem).isNotNull();
        testEntityManager.detach(genreTem);

        Genre genre = genreRepository.getGenre(genreTem.getName());

        assertThat(genre).isNotNull();
        assertThat(genre.equals(genreTem));
    }

}