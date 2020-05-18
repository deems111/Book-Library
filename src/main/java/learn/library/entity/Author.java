package learn.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;
    private List<Book> books;
    private String surname;
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Author author = (Author) object;
        return getName() != null ? getName().equalsIgnoreCase(author.getName()) : author.getName() == null &&
                getSurname() != null ? getSurname().equalsIgnoreCase(author.getSurname()) : author.getSurname() == null;
    }

    @Override
    public int hashCode() {
        return 99 * ((getName() != null ? getName().hashCode() : 0) + (getSurname() != null ? getSurname().hashCode() : 0));
    }

}
