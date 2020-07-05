package learn.library.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Document(collection = "books")
public class Book {

    private String id;

    private String title;

    private String genre;

    private Set<String> authors;

    public Book() {
    }

    public Book(String title, String genre, Set<String> authors) {
        this.title = title;
        this.genre = genre;
        this.authors = authors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(getTitle(), book.getTitle()) &&
                authorsEquals(book);
    }

    private boolean authorsEquals(Book book) {
        if (book.getAuthors().size() != getAuthors().size()) {
            return false;
        }
        return listEquals(new ArrayList<>(getAuthors()),
                new ArrayList<>(book.getAuthors()));
    }

    private boolean listEquals(List<String> authors, List<String> anotherAuthors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals(anotherAuthors.get(i))) {
                return false;
            }
        }
        return true;
    }

}
