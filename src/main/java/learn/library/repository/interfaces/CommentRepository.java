package learn.library.repository.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("DELETE FROM Comment c WHERE c.book = :book")
    @Modifying
    void deleteCommentsToBook(@Param(value = "book") Book book);

    @Query("SELECT c FROM Comment c JOIN c.book b WHERE b.id = :bookId")
    List<Comment> getCommentsByBookId(long bookId);

}
