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
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
public class LibraryImpl implements Library {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;

    public LibraryImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, CommentDao commentDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.commentDao = commentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Author author) {
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title) {
        return bookDao.getBooksByTitle(title);
    }

    @Override
    public Book getBooksById(long id) {
        return bookDao.getBooksById(id);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    @Transactional
    public long addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    @Transactional
    public long addGenre(Genre genre) {
        return genreDao.addGenre(genre);
    }

    @Transactional(readOnly = true)
    public Genre getGenre(String genre) {
        return genreDao.getGenre(genre);
    }

    @Override
    @Transactional
    public long addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long id) {
        commentDao.deleteComment(id);
    }

    @Override
    @Transactional
    public void deleteCommentByBookId(long bookId) {
        commentDao.deleteCommentsToBook(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentDao.getCommentsByBookId(bookId);
    }

}
