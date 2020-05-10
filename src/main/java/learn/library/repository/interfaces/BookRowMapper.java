package learn.library.repository.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import learn.library.util.Utility;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRowMapper implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        long previousBookId = -1L;
        Book book = new Book();
        boolean firstResult = true;
        boolean rsHasBook = false;

        while (rs.next()) {
            if (previousBookId != rs.getLong("ID_BOOK")) {
                if (!firstResult) {
                    book.setAuthors(authors);
                    books.add(book);
                    book = new Book();
                    authors = new ArrayList<>();
                }
                firstResult = false;
                rsHasBook = true;
                book.setId(rs.getLong("ID_BOOK"));
                book.setName(rs.getString("TITLE"));
                book.setGenre(new Genre(rs.getLong("ID_GENRE"), rs.getString("GENRE")));
                authors.add(new Author(rs.getLong("ID_AUTHOR"), rs.getLong("ID_BOOK"), rs.getString("SURNAME"), rs.getString("NAME")));
                previousBookId = rs.getLong("ID_BOOK");

            } else {
                if (Utility.isNotEmpty(rs.getLong("ID_AUTHOR"))) {
                    authors.add(new Author(rs.getLong("ID_AUTHOR"), rs.getLong("ID_BOOK"), rs.getString("SURNAME"), rs.getString("NAME")));
                }
            }
        }
        if (rsHasBook) {
            book.setAuthors(authors);
            books.add(book);
        }

        return books;
    }

}
