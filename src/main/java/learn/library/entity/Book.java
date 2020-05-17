package learn.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GENRE")
    private Genre genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="book", cascade=CascadeType.PERSIST)
    private Set<Author> authors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="book", cascade = CascadeType.PERSIST)
    private Set<Comment> comments;

    public Book() {
    }

    public Book(String title, Genre genre, Set<Author> authors, Set<Comment> comments) {
        this.title = title;
        this.genre = genre;
        this.authors = authors;
        this.comments = comments;
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
        Author.AuthorComparator comparator = new Author.AuthorComparator();
        List<Author> authors = new ArrayList<>(getAuthors());
        List<Author> anotherAuthors = new ArrayList<>(book.getAuthors());
        authors.sort(comparator);
        anotherAuthors.sort(comparator);

        return listEquals(authors, anotherAuthors);
    }

    private boolean listEquals(List<Author> authors, List<Author> anotherAuthors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).getSurname().equalsIgnoreCase(anotherAuthors.get(i).getSurname())
                    || !authors.get(i).getName().equalsIgnoreCase(anotherAuthors.get(i).getName())) {
                return false;
            }
        }
        return true;
    }

}
