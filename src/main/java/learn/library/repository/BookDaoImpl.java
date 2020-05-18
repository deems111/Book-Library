package learn.library.repository;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.BookDao;
import learn.library.repository.interfaces.BookResultSetExtractor;
import learn.library.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoImpl implements BookDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private final AuthorDao authorDao;

    private static final String SELECT_BASE_QUERY = "SELECT b.ID as ID_BOOK, a.ID as ID_AUTHOR, g.ID as ID_GENRE, b.*, a.*, g.* " +
            "FROM BOOK b LEFT JOIN BOOK_AUTHOR ba ON b.id = ba.ID_BOOK LEFT JOIN AUTHOR a ON ba.ID_AUTHOR = a.ID LEFT JOIN GENRE g ON b.ID_GENRE = g.ID WHERE 1=1 ";
    private static final String SELECT_BY_AUTHOR = "and UPPER(a.NAME) like :name and UPPER(a.SURNAME) like :surname";
    private static final String SELECT_BY_TITLE = "and UPPER (b.TITLE) like :title";
    private static final String SELECT_BY_ID = "and b.ID = :ID_BOOK";

    @Override
    public List<Book> getBooks() {
        return jdbcTemplate.query(SELECT_BASE_QUERY, new BookResultSetExtractor());
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        if (Utility.isNotEmpty(author) && Utility.isNotEmpty(author.getId())) {
            return jdbcTemplate.query(SELECT_BASE_QUERY + SELECT_BY_AUTHOR,
                    new MapSqlParameterSource().addValue("surname", "%" + author.getSurname().toUpperCase() + "%")
                            .addValue("name", "%" + author.getName().toUpperCase() + "%"),
                    new BookResultSetExtractor());
        }
        return new ArrayList<Book>();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return jdbcTemplate.query(SELECT_BASE_QUERY + SELECT_BY_TITLE,
                new MapSqlParameterSource().addValue("title", "%" + title.toUpperCase() + "%"),
                new BookResultSetExtractor());
    }

    @Override
    public void deleteBookById(long id) {
        jdbcTemplate.update("DELETE FROM BOOK WHERE ID =:id", new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public long addBook(Book book) {
        //columns with autoincrement values
        String[] generatedColumns = {"id"};
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO BOOK (TITLE, ID_GENRE) VALUES (:title, :idGenre)",
                new MapSqlParameterSource().addValue("title", book.getName())
                        .addValue("idGenre", book.getGenre().getId()),
                keyHolder, generatedColumns);

        long bookId = keyHolder.getKey().longValue();

        for (Author author : book.getAuthors()) {
            long authorId = authorDao.addAuthor(author);
            jdbcTemplate.update("INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (:idBook, :idAuthor)",
                    new MapSqlParameterSource().addValue("idBook", bookId)
                            .addValue("idAuthor", authorId));
        }

        return bookId;
    }

}
