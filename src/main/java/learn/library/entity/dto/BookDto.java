package learn.library.entity.dto;

import learn.library.entity.Author;
import learn.library.entity.Book;
import lombok.Data;

import java.util.Set;

@Data
public class BookDto {

    private Long id;
    private String authors;
    private String title;
    private String genre;

    public BookDto(Book book) {
        this.id = book.getId();
        this.authors = setAuthors(book.getAuthors());
        this.title = book.getTitle();
        genre = book.getGenre().getName();
    }

    private String setAuthors(Set<Author> authors) {
        StringBuffer result = new StringBuffer();
        for (Author author: authors) {
            result.append(author.getSurname()).append(" ")
                    .append(author.getName()).append(";");
        }
        return result.toString();
    }
}
