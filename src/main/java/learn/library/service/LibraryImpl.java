package learn.library.service;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.repository.interfaces.*;
import learn.library.service.interfaces.Library;
import learn.library.util.ConvertUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Data
public class LibraryImpl implements Library {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    @Autowired
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
        Set<Author> authors = new HashSet<>();
        for (Author author : book.getAuthors()) {
            authors.add(authorRepository.save(author));
        }
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Transactional
    public Book addBook(String authors,String title, String genre) {
        Book book = new Book(title, setGenreForAddBook(genre), ConvertUtil.convertAuthorsArrayToSet(authors));
        if(!isBookExist(book)) {
            return addBook(book);
        }
        return null;
    }

    @Override
    @Transactional
    public Book updateBook(Long id, String authors, String title, String genre) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book updatedBook = null;
        try {
            updatedBook = optionalBook.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Set<Author> authorSet = ConvertUtil.convertAuthorsArrayToSet(authors);
        updatedBook.setAuthors(authorSet);
        for (Author author : authorSet) {
            authorRepository.save(author);
        }
        updatedBook.setTitle(title);
        updatedBook.setGenre(setGenreForAddBook(genre));

        return bookRepository.save(updatedBook);
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

    @Override
    @Transactional()
    public Genre setGenreForAddBook(String genre) {
        Genre genreAdd = getGenre(genre);
        if (genreAdd == null) {
            genreAdd = addGenre(new Genre(genre));
        }
        return genreAdd;
    }

    private boolean isBookExist(Book addBook) {
        List<Book> books = getBooksByTitle(addBook.getTitle());
        if (!books.isEmpty()) {
            for (Book book : books) {
                if (book.equals(addBook)) {
                    return true;
                }
            }

        }
        return false;
    }

}
