package learn.library.entity.splDbEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class CommentSqlDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject;

    @JoinColumn(name = "ID_BOOK")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookSqlDb book;

    public CommentSqlDb(String name, String subject, BookSqlDb book) {
        this.name = name;
        this.subject = subject;
        this.book = book;
    }

}