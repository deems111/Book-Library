package learn.library.repository.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    void deleteAllByBook(Book book);

    List<Comment> findCommentsByBook(Book book);

}
