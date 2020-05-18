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

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

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
                Author author = new Author();
                    author.setId(rs.getLong("ID_AUTHOR"));
                    author.setSurname(rs.getString("SURNAME"));
                    author.setName(rs.getString("NAME"));
                    authors.add(author);
                    previousBookId = rs.getLong("ID_BOOK");

            } else {
                if (Utility.isNotEmpty(rs.getLong("ID_AUTHOR"))) {
                    Author author = new Author();
                    author.setId(rs.getLong("ID_AUTHOR"));
                    author.setSurname(rs.getString("SURNAME"));
                    author.setName(rs.getString("NAME"));
                    authors.add(author);
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
