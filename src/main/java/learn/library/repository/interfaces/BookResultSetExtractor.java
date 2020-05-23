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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

    Map<Long, Book> booksMap = new HashMap<>();

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Author> authors;
        while (rs.next()) {
            Long id = rs.getLong("ID_BOOK");
            Book book = booksMap.get(id);
            if (book != null) {
                authors = book.getAuthors();
                extractAuthorData(rs, authors, book);
            } else {
                book = new Book();
                book.setId(rs.getLong("ID_BOOK"));
                book.setName(rs.getString("TITLE"));
                book.setGenre(new Genre(rs.getLong("ID_GENRE"), rs.getString("GENRE")));
                authors = new ArrayList<>();
                extractAuthorData(rs, authors, book);
                booksMap.put(id, book);
            }
        }
        return new ArrayList<>(booksMap.values());
    }

    private void extractAuthorData(ResultSet rs, List<Author> authors, Book book) throws SQLException, DataAccessException {
        if (Utility.isNotEmpty(rs.getLong("ID_AUTHOR"))) {
            Author author = new Author();
            author.setId(rs.getLong("ID_AUTHOR"));
            author.setSurname(rs.getString("SURNAME"));
            author.setName(rs.getString("NAME"));
            authors.add(author);
        }
        book.setAuthors(authors);
    }

}
