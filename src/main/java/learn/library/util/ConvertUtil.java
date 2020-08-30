package learn.library.util;

import learn.library.entity.Author;
import learn.library.entity.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertUtil {

    /**
     * Method convert shell strings with Author's name and surname to object of Author.class
     */
    public static Set<Author> convertAuthorsArrayToSet(String authorStr) {
        Set<Author> authors = new HashSet<>();
        String[] nameSurname = authorStr.split(";");
        try {
            for (String str : nameSurname) {
                String[] author = str.split(" ");
                authors.add(new Author(null, author[0], author[1]));
            }
        }
        catch (Exception e ) {e.printStackTrace();}
        return authors;
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
        for (Author author : book.getAuthors()) {
            stringBuilder.append(author.getSurname()).append(" ").append(author.getName()).append(";\t");
        }
        stringBuilder.append("Genre: ");
        stringBuilder.append("Genre: ");
        stringBuilder.append(book.getGenre().getName() + "\t");
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
                for (Author author : book.getAuthors()) {
                    stringBuilder.append(author.getSurname()).append(" ").append(author.getName()).append(";\t");
                }
                stringBuilder.append("Genre: ");
                stringBuilder.append(book.getGenre().getName()).append("\t");
            }
        } else {
            stringBuilder.append("\nNo books found");
        }

        return stringBuilder.toString();
    }


}
