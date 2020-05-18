package learn.library.repository;

import learn.library.entity.Genre;
import learn.library.repository.interfaces.GenreDao;
import learn.library.repository.interfaces.GenreRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDaoImpl implements GenreDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //columns with autoincrement values
    private String[] generatedColumns = {"id"};

    @Override
    public long addGenre(Genre genre) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO GENRE (GENRE) VALUES (:genre)",
                new MapSqlParameterSource().addValue("genre", genre.getName()),
                keyHolder, generatedColumns);


        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteGenre(long id) {
        jdbcTemplate.update("DELETE FROM GENRE WHERE ID =:idGenre", new MapSqlParameterSource().addValue("idGenre", id));
    }

    /**
     * When throw 'org.springframework.dao.EmptyResultDataAccessException' exception, return null
     */
    @Override
    public Genre getGenre(String genreName) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM GENRE WHERE GENRE = :genre_name",
                    new MapSqlParameterSource().addValue("genre_name", genreName),
                    new GenreRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}