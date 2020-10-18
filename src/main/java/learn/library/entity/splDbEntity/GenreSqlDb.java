package learn.library.entity.splDbEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GENRE")
public class GenreSqlDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "genre")
    private String name;

    public GenreSqlDb(String name) {
        this.name = name;
    }

}