package learn.library.repository.interfaces;

import learn.library.entity.Author;

import learn.library.util.Utility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (Utility.isNotEmpty(rs.getLong("ID"))) {
            return new Author(rs.getInt("ID"), rs.getInt("ID_BOOK"), rs.getString("SURNAME"), rs.getString("NAME"));
        }
        return null;
    }

}
