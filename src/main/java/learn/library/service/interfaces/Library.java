package learn.library.service.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Role;
import learn.library.entity.User;
import learn.library.entity.enums.ERole;

import java.util.List;

/**
 * Interface for library service
 */
public interface Library {

    List<Book> getBooks();

    Book getBooksById(String id);

    void deleteBookById(String id);

    Book addBook(Book book);

    void deleteCommentByBookId(String bookId);

    List<Comment> getCommentsByBookId(String bookId);

    Book updateBook(Book book);

    User addUser(User user);

    User getUserByName(String name);

    Role getRoleByName(ERole eRole);
}


