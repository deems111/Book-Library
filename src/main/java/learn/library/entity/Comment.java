package learn.library.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject;

    @JoinColumn(name = "ID_BOOK")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public Comment(String name, String subject, Book book) {
        this.name = name;
        this.subject = subject;
        this.book = book;
    }

}
