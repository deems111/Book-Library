package learn.library.repository;

import learn.library.entity.Author;

import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.AuthorRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long addAuthor(Author author) {
        Author existingAuthor = getAuthor(author.getSurname(), author.getName());
        if (existingAuthor != null) {
            return existingAuthor.getId();
        }

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO AUTHOR (SURNAME, NAME) VALUES (:surname, :name)",
                new MapSqlParameterSource().addValue("surname", author.getSurname())
                        .addValue("name", author.getName()),
                keyHolder, new String[]{"id"});

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
