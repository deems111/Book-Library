package learn.library.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "BOOK")
@NamedEntityGraph(name = "book_genre_entity_graph",
        attributeNodes = {@NamedAttributeNode("genre")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GENRE")
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "ID_BOOK"), inverseJoinColumns = @JoinColumn(name = "ID_AUTHOR"))
    private Set<Author> authors;

    public Book() {
    }

    public Book(String title, Genre genre, Set<Author> authors) {
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

    private boolean listEquals(List<Author> authors, List<Author> anotherAuthors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals(anotherAuthors.get(i))) {
                return false;
            }
        }
        return true;
    }

}
