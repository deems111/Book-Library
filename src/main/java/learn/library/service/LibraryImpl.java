package learn.library.service;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.repository.interfaces.*;
import learn.library.service.interfaces.Library;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class LibraryImpl implements Library {

    private  BookDao bookDao;
    private  AuthorDao authorDao;
    private  GenreDao genreDao;
    private  CommentDao commentDao;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    public LibraryImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                       CommentRepository commentRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Author author) {
        Author authorFromDB = authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
        return bookRepository.findByAuthors(authorFromDB);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBooksById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        for (Author author : book.getAuthors()) {
            authorRepository.save(author);
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    public Genre getGenre(String genre) {
        return genreRepository.getGenre(genre);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCommentByBookId(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return;
        }
        commentRepository.deleteCommentsToBook(optionalBook.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepository.getCommentsByBookId(bookId);
    }

}
