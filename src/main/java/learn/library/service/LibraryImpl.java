package learn.library.service;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.BookDao;
import learn.library.repository.interfaces.CommentDao;
import learn.library.repository.interfaces.GenreDao;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class LibraryImpl implements Library {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookDao.getBooksByTitle(title);
    }

    @Override
    public Book getBooksById(long id) {
        return bookDao.getBooksById(id);
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

    public Genre getGenre(String genre) {
        return genreDao.getGenre(genre);
    }

    @Override
    public long addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public void deleteComment(long id) {
        commentDao.deleteComment(id);
    }

    @Override
    public void deleteCommentByBookId(long bookId) {
        commentDao.deleteCommentsToBook(bookId);
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentDao.getCommentsByBookId(bookId);
    }

}
