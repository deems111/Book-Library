package learn.library.repository.interfaces;

import learn.library.entity.Genre;
import learn.library.util.Utility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (Utility.isNotEmpty(rs.getLong("ID"))) {
            return new Genre(rs.getLong("ID"), rs.getString("GENRE"));
        }
        return null;
    }

}
