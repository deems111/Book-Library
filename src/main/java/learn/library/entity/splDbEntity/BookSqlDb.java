package learn.library.entity.splDbEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class BookSqlDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GENRE")
    private GenreSqlDb genre;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "ID_BOOK"), inverseJoinColumns = @JoinColumn(name = "ID_AUTHOR"))
    private Set<AuthorSqlDb> authors;

    public BookSqlDb(String title, GenreSqlDb genre, Set<AuthorSqlDb> authors) {
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
        BookSqlDb book = (BookSqlDb) obj;
        return Objects.equals(getTitle(), book.getTitle()) &&
                authorsEquals(book);
    }

    private boolean authorsEquals(BookSqlDb book) {
        if (book.getAuthors().size() != getAuthors().size()) {
            return false;
        }
        return listEquals(new ArrayList<>(getAuthors()),
                new ArrayList<>(book.getAuthors()));
    }

    private boolean listEquals(List<AuthorSqlDb> authors, List<AuthorSqlDb> anotherAuthors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!authors.get(i).equals(anotherAuthors.get(i))) {
                return false;
            }
        }
        return true;
    }

}
