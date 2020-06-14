package learn.library.repository;

import learn.library.entity.Author;
import learn.library.repository.interfaces.AuthorDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public long addAuthor(Author author) {
        em.persist(author);
        return getAuthor(author).getId();
    }

    @Override
    public void deleteAuthor(long bookId) {
        for (Author author : getAuthorsByBookId(bookId)) {
            em.remove(author);
        }
    }

    /**
     * When throw 'org.springframework.dao.EmptyResultDataAccessException' exception, return null
     */
    @Override
    public Author getAuthor(Author author) {
        try {
            TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.surname = :surname AND a.name = :name",
                    Author.class);
            query.setParameter("surname", author.getSurname());
            query.setParameter("name", author.getName());
            return query.getSingleResult();
        } catch (NoResultException exception) {
        }
        return null;
    }

    @Override
    public Set<Author> getAuthorsByBookId(long bookId) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN Book b WHERE b.id = :bookId",
                Author.class);
      //  query.setParameter("bookId", bookId);
        return new HashSet<Author>(query.getResultList());
    }

}
