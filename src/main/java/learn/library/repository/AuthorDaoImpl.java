package learn.library.repository;

import learn.library.entity.Author;

import learn.library.entity.Genre;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.AuthorRowMapper;
import learn.library.repository.interfaces.GenreRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //columns with autoincrement values
    private String[] generatedColumns = {"id"};

    @Override
    public long addAuthor(Author author) {
        Author existingAuthor =getAuthor(author.getSurname(), author.getName());
        if (existingAuthor != null) {
            return existingAuthor.getId();
        }

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO AUTHOR (SURNAME, NAME) VALUES (:surname, :name)",
                new MapSqlParameterSource().addValue("surname", author.getSurname())
                        .addValue("name", author.getName()),
                keyHolder, generatedColumns);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteAuthor(long id) {
        jdbcTemplate.update("DELETE FROM AUTHOR WHERE ID =:ID",
                new MapSqlParameterSource().addValue("ID", id));
    }

    /**
     * When throw 'org.springframework.dao.EmptyResultDataAccessException' exception, return null
     */
    @Override
    public Author getAuthor(String surname, String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM AUTHOR WHERE NAME = :name AND SURNAME = :surname",
                    new MapSqlParameterSource().addValue("name", name)
                            .addValue("surname", surname),
                    new AuthorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
