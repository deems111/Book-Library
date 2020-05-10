package learn.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;
    private long bookId;
    private String surname;
    private String name;

    public static class AuthorComparator implements Comparator<Author> {

        @Override
        public int compare(Author author, Author anotherAuthor) {
            if (!author.getSurname().equalsIgnoreCase(anotherAuthor.getSurname())) {
                return compareSurname(author.getSurname(), anotherAuthor.getSurname());
            } else return compareName(author.getName(), anotherAuthor.getName());
        }

        private int compareSurname(String surname, String anotherSurname) {
            return surname.compareTo(anotherSurname);
        }

        private int compareName(String name, String anotherName) {
            return name.compareTo(anotherName);
        }

    }

}
