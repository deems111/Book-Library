package learn.library.util;

import learn.library.entity.Book;
import learn.library.entity.Comment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShellConvertUtil {

    public static String convertCommentsToShellString(List<Comment> comments) {
        StringBuilder stringBuilder = new StringBuilder("Comments listing");
        if (comments.size() > 0) {
            for (Comment comment : comments) {
                stringBuilder.append("\nAuthor's name: ").append(comment.getName());
                stringBuilder.append("\tSubject: ").append(comment.getSubject());
            }
        } else {
            stringBuilder.append("\nNo comments to book found");
        }
        return stringBuilder.toString();
    }

    /**
     * Method convert entity of book into table to show using shell
     */
    public static String convertListToShellString(List<Book> books) {
        StringBuilder stringBuilder = new StringBuilder("Books listing");
        if (books.size() > 0) {
            for (Book book : books) {
                stringBuilder.append("\nTitle: ").append(book.getTitle()).append("\t");
                stringBuilder.append("Author(s): ");
                for (String author : book.getAuthors()) {
                    stringBuilder.append(author).append(";\t");
                }
                stringBuilder.append("Genre: ");
                stringBuilder.append(book.getGenre()).append("\t");
            }
        } else {
            stringBuilder.append("\nNo books found");
        }

        return stringBuilder.toString();
    }

    /**
     * Method convert entity of book into table to show using shell
     */
    public static String convertBookToShellString(Book book) {
        if (book == null) {
            return "No book(s) found";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title: ").append(book.getTitle()).append("\t");
        stringBuilder.append("Author(s): ");
        for (String author : book.getAuthors()) {
            stringBuilder.append(author).append(";\t");
        }
        stringBuilder.append("Genre: ");
        stringBuilder.append(book.getGenre() + "\t");
        return stringBuilder.toString();
    }

    /**
     * Method convert shell strings with Author's name and surname to object of Author.class
     */
    public static Set<String> convertAuthorsArrayToSet(String authorStr) {
        Set<String> authors = new HashSet<>();
        String[] nameSurname = authorStr.split(";");
        for (String str : nameSurname) {
            String[] author = str.split(",");
            authors.add(author[0] + " " + author[1]);
        }

        return authors;
    }

}
