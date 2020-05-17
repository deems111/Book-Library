package learn.library.repository.interfaces;

import learn.library.entity.Comment;

import java.util.List;

public interface CommentDao {

    long addComment(Comment comment);

    List<Comment> getCommentsByBookId(long bookId);

    Comment getComment(long id);

    boolean deleteCommentsToBook(long bookId);

    void deleteComment(long id);

}
