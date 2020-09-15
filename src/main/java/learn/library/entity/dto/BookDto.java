package learn.library.entity.dto;

import learn.library.entity.Author;
import learn.library.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class BookDto {

    private Long id;
    private String authorString;
    private String title;
    private String genre;

    public BookDto(Book book) {
        this.id = book.getId();
        this.authorString = setAuthors(book.getAuthors());
        this.title = book.getTitle();
        this.genre = book.getGenre().getName();
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
