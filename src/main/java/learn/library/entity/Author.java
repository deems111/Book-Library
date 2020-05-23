package learn.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @EqualsAndHashCode.Exclude
    private long id;
    private String surname;
    private String name;

}
