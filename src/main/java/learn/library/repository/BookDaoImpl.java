package learn.library.repository;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.repository.interfaces.AuthorDao;
import learn.library.repository.interfaces.BookDao;
import learn.library.repository.interfaces.CommentDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private final EntityManager em;
    private final AuthorDao authorDao;
    private final CommentDao commentDao;

    public BookDaoImpl(EntityManager em, AuthorDao authorDao, CommentDao commentDao) {
        this.em = em;
        this.authorDao = authorDao;
        this.commentDao = commentDao;
    }

    private static final String SELECT_BY_AUTHOR = "SELECT distinct b FROM Book b JOIN b.authors a WHERE a.id = :id";
    private static final String SELECT_BY_TITLE = "SELECT b FROM Book b WHERE UPPER(b.title) like :title";

    @Override
    public List<Book> getBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        Author authorFromDb = authorDao.getAuthor(author);

        TypedQuery<Book> query = em.createQuery(SELECT_BY_AUTHOR, Book.class);
        query.setParameter("id", authorFromDb.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(SELECT_BY_TITLE, Book.class);
        query.setParameter("title", getLikeString(title));
        return query.getResultList();
    }

    @Override
    public Book getBooksById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

    @Override
    public long addBook(Book book) {
        for (Author author : book.getAuthors()) {
            Author authorFromDb = authorDao.getAuthor(author);
            if (authorFromDb == null) {
                authorDao.addAuthor(author);
            }
        }
        em.persist(book);
        Book addedBook = em.find(Book.class, book.getId());
        return addedBook.getId();
    }

    private String getLikeString(String string) {
        return "%" + string.toUpperCase() + "%";
    }

}
