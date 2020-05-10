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
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO AUTHOR (SURNAME, NAME, ID_BOOK) VALUES (:surname, :name, :idBook)",
                new MapSqlParameterSource().addValue("surname", author.getSurname())
                        .addValue("name", author.getName())
                        .addValue("idBook", author.getBookId()),
                keyHolder, generatedColumns);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteAuthor(long bookId) {
        jdbcTemplate.update("DELETE FROM AUTHOR WHERE ID_BOOK =:ID_BOOK",
                new MapSqlParameterSource().addValue("ID_BOOK", bookId));
    }

    /**
     * When throw 'org.springframework.dao.EmptyResultDataAccessException' exception, return null
     */
    @Override
    public Author getAuthor(Author author) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM AUTHOR WHERE NAME = :name AND SURNAME = :surname",
                    new MapSqlParameterSource().addValue("name", author.getName())
                            .addValue("surname", author.getSurname()),
                    new AuthorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
