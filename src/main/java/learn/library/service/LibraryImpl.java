package learn.library.service;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.BookDao;
import learn.library.repository.interfaces.GenreDao;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class LibraryImpl implements Library {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public LibraryImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String surname, String name) {
        Author author = authorDao.getAuthor(surname, name);
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookDao.getBooksByTitle(title);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public long addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public long addGenre(Genre genre) {
        return genreDao.addGenre(genre);
    }

    @Override
    public void deleteGenre(long id) {
        genreDao.deleteGenre(id);
    }

    public Genre getGenre(String genre) {
        return genreDao.getGenre(genre);
    }

}
