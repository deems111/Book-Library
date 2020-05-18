package learn.library.model;

import learn.library.entity.Author;
import learn.library.entity.Book;
import learn.library.entity.Genre;

import learn.library.service.interfaces.Library;
import learn.library.util.Utility;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@Data
public class ShellModel {

    @Autowired
    private Library library;

    /**
     * Method return table, including of all books in library
     *
     * @return String
     */
    @ShellMethod(value = "Get books", key = "get")
    public String getAllBooks() {
        return convertListToShellString(library.getBooks());
    }

    @ShellMethod(value = "Get books by Title", key = "title")
    public String getBooksByTitle(@ShellOption String title) {
        if (Utility.isNotEmpty(title)) {
            return convertListToShellString(library.getBooksByTitle(title));
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
        if (Utility.isNotEmpty(surname) || Utility.isNotEmpty(name)) {
            return convertListToShellString(library.getBooksByAuthor(surname, name));
        }
        return "Error. Enter Author's name or surname";
    }

    /**
     * Method remove book from library
     *
     * @param id id of book ot remove
     * @return String
     */
    @ShellMethod(value = "Delete book", key = "delete")
    public String deleteBook(@ShellOption Long id) {
        library.deleteBookById(id);
        return "Book with id " + id + " was deleted";
    }

    /**
     * Method remove genre from library, no books should be in library references to id genre
     *
     * @param id id of book ot genre
     * @return String
     */
    @ShellMethod(value = "Delete genre", key = "deleteGenre")
    public String deleteGenre(@ShellOption Long id) {
        library.deleteGenre(id);
        return "Genre with id " + id + " was deleted";
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

        Book book = new Book();
        book.setName(title);
        book.setAuthors(convertAuthorsArrayToList(author));

        if (isBookExist(book)) {
            return "Book already exist";
        }

        book.setGenre(setGenreForAddBook(genre));

        long id = library.addBook(book);
        return "Book was added with id = " + id;
    }

    /**
     * Add genre to library
     *
     * @param genreStr title of genre
     * @return String
     */
    @ShellMethod(value = "Add genre", key = "addGenre")
    public String addGenre(@ShellOption String genreStr) {
        Genre genre = new Genre();
        genre.setName(genreStr);
        library.addGenre(genre);
        return "Genre " + genreStr + " was added";
    }

    /**
     * Method convert entity of book into table to show using shell
     */
    public static String convertListToShellString(List<Book> books) {
        StringBuilder stringBuilder = new StringBuilder("Books listing");
        if (books.size() > 0) {
            for (Book book : books) {
                stringBuilder.append("\nTitle: ").append(book.getName()).append("\t");
                stringBuilder.append("Author(s): ");
                for (Author author : book.getAuthors()) {
                    stringBuilder.append(author.getSurname()).append(" ").append(author.getName()).append(";\t");
                }
                stringBuilder.append("Genre: ");
                stringBuilder.append(book.getGenre().getName()).append("\t");
            }
        } else {
            stringBuilder.append("No books found");
        }

        return stringBuilder.toString();
    }

    /***
     * Method convert shell strings with Author's name and surname to object of Author.class
     */
    public static List<Author> convertAuthorsArrayToList(String authorStr) {
        List<Author> authors = new ArrayList<>();
        String[] nameSurname = authorStr.split(";");
        for (String str : nameSurname) {
            String[] authorArrayString = str.split(",");
            Author author = new Author();
            author.setSurname(authorArrayString[0]);
            author.setName(authorArrayString[1]);
            authors.add(author);
        }

        return authors;
    }

    private Genre setGenreForAddBook(String genre) {
        Genre genreAdd = library.getGenre(genre);
        if (genreAdd == null) {
            genreAdd = new Genre(library.addGenre(new Genre(-1L, genre)), genre);
        }
        return genreAdd;
    }

    private boolean isBookExist(Book addBook) {
        List<Book> books = library.getBooksByTitle(addBook.getName());
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
