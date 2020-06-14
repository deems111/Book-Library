package learn.library.repository;

import learn.library.entity.Comment;
import learn.library.repository.interfaces.CommentDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public long addComment(Comment comment) {
        em.persist(comment);
        return em.find(Comment.class, comment.getId()).getId();
    }

    @Override
    public Comment getComment(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c JOIN c.book b WHERE b.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean deleteCommentsToBook(long bookId) {
        boolean result = false;
        for (Comment comment : getCommentsByBookId(bookId)) {
            em.remove(comment);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteComment(long id) {
        Comment comment = getComment(id);
        if (comment != null) {
            em.remove(comment);
        }
    }

}
