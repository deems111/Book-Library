package learn.library.repository;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.BookDao;
import learn.library.repository.interfaces.CommentDao;
import learn.library.repository.interfaces.GenreDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private final EntityManager em;
    @Autowired
    private final AuthorDao authorDao;
    @Autowired
    private final CommentDao commentDao;

    private static final String SELECT_BY_AUTHOR = "SELECT distinct b FROM Book b JOIN b.authors a WHERE a.id = :id";
    private static final String SELECT_BY_TITLE = "SELECT b FROM Book b WHERE UPPER(b.title) like :title";

    @Override
    public List<Book> getBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        Author authorFromDb = authorDao.getAuthor(author);
        if (authorFromDb == null) {
            return new ArrayList<>();
        }

        TypedQuery<Book> query = em.createQuery(SELECT_BY_AUTHOR, Book.class);
        query.setParameter("id", authorFromDb.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(SELECT_BY_TITLE, Book.class);
        query.setParameter("title",  getLikeString(title));
        return query.getResultList();
    }

    @Override
    public Book getBooksById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
     //       authorDao.deleteAuthor(id);
            commentDao.deleteCommentsToBook(id);
            em.remove(book);
        }
    }

    @Override
    @Transactional
    public long addBook(Book book) {
        for(Author author : book.getAuthors()) {
            Author authorFromDb = authorDao.getAuthor(author);
            if(authorFromDb == null) {
                authorDao.addAuthor(author);
            }
        }
        em.persist(book);
        Book addedBook =  em.find(Book.class, book.getId());
        return addedBook.getId();
    }

    private String getLikeString (String string) {
        return "%"+ string.toUpperCase() + "%";
    }

}
