package learn.library.model;

import learn.library.entity.Book;
import learn.library.entity.Comment;

import learn.library.service.interfaces.Library;
import learn.library.util.ShellConvertUtil;
import learn.library.util.Utility;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@Data
public class ShellModel {

    private final Library library;

    @Autowired
    public ShellModel(Library library) {
        this.library = library;
    }

    /**
     * Method return table, including of all books in library
     *
     * @return String
     */
    @ShellMethod(value = "Get books", key = "get")
    public String getAllBooks() {
        return ShellConvertUtil.convertListToShellString(library.getBooks());
    }

    @ShellMethod(value = "Get books by Title", key = "title")
    public String getBooksByTitle(@ShellOption String title) {
        if (Utility.isNotEmpty(title)) {
            return ShellConvertUtil.convertListToShellString(library.getBooksByTitle(title));
        }
        return "Not found by Title :" + title;
    }

    /**
     * Method return table, including of books in library with current author
     *
     * @param surname surname of author
     * @param name    name of author
     * @return String
     */
    @ShellMethod(value = "Get books by Author", key = "author")
    public String getBooksByAuthor(@ShellOption String surname, String name) {
        return ShellConvertUtil.convertListToShellString(library.getBooksByAuthor(surname + " " + name));
    }

    @ShellMethod(value = "Get By Id", key = "id")
    public String getBookById(@ShellOption String bookId) {
        return ShellConvertUtil.convertBookToShellString(library.getBooksById(bookId));
    }

    /**
     * Method remove book from library
     *
     * @param id id of book ot remove
     * @return String
     */
    @ShellMethod(value = "Delete book", key = "delete")
    public String deleteBook(@ShellOption String id) {
        library.deleteBookById(id);
        return "Book with id " + id + " was deleted";
    }

    /**
     * Add book to library
     * Book format:
     * title authors genre
     * Enter book authors in format Surname,Name;Surname1,Name1;
     *
     * @param title  title of book
     * @param author author surname and name
     * @param genre  genre of book
     * @return String
     */
    @ShellMethod(value = "Add Book", key = "addBook")
    public String addNewBook(
            @ShellOption String title,
            @ShellOption String author,
            @ShellOption String genre) {


        Book book = new Book(title, genre, ShellConvertUtil.convertAuthorsArrayToSet(author));

        if (isBookExist(book)) {
            return "Book already exist";
        }

        String id = library.addBook(book).getId();
        return "Book was added with id = " + id;
    }

    @ShellMethod(value = "Add comment", key = "addComment")
    public String addComment(@ShellOption String bookId,
                             @ShellOption String name,
                             @ShellOption String subject) {
        Book book = library.getBooksById(bookId);
        if (book == null) {
            return "Book with id = " + bookId + " not found";
        }

        Comment comment = new Comment(name, subject, book);
        String commentId = library.addComment(comment).getId();
        return "Comment added successfully with id = " + commentId;
    }

    @ShellMethod(value = "Delete comment", key = "deleteComment")
    public String deleteComment(@ShellOption String id) {
        library.deleteComment(id);
        return "Comment deleted successfully";
    }

    @ShellMethod(value = "Delete all comment to book", key = "deleteCommentBook")
    public String deleteCommentsToBook(@ShellOption String bookId) {
        library.deleteCommentByBookId(bookId);
        return "Comment to book with id = " + bookId + " deleted successfully";
    }

    @ShellMethod(value = "Get all comments to book", key = "getCommentBook")
    public String getCommentsToBook(@ShellOption String bookId) {
        return ShellConvertUtil.convertCommentsToShellString(library.getCommentsByBookId(bookId));
    }

    private boolean isBookExist(Book addBook) {
        List<Book> books = library.getBooksByTitle(addBook.getTitle());
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
