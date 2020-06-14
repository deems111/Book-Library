package learn.library.repository;

import learn.library.entity.Genre;
import learn.library.repository.interfaces.GenreDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public long addGenre(Genre genre) {
        em.persist(genre);
        return getGenre(genre.getName()).getId();
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
    }

    /**
     * When throw 'javax.persistence.NoResultException' exception, return null
     */
    @Override
    public Genre getGenre(String genreName) {
        try {
            TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g WHERE g.name = :genreName",
                    Genre.class);
            query.setParameter("genreName", genreName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

}