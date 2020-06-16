package learn.library.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Book {

    private long id;
    private String name;
    private Genre genre;
    private List<Author> authors;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(getName(), book.getName()) &&
                authorsEquals(book);
    }

    private boolean authorsEquals(Book book) {
        if (book.getAuthors().size() != getAuthors().size()) {
            return false;
        }

        List<Author> authors = getAuthors();
        List<Author> anotherAuthors = book.getAuthors();
        return listEquals(authors, anotherAuthors);
    }

    private boolean listEquals(List<Author> authors, List<Author> anotherAuthors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals(anotherAuthors.get(i))) {
                return false;
            }
        }
        return true;
    }

}
