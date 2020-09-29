package learn.library.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
@Builder
public class Book {

    @Id
    @EqualsAndHashCode.Exclude
    private String id;
    @Field
    private String title;
    @Field
    private String genre;
    @Field
    private List<String> authors;

    public Book(String title, String genre, List<String> authors) {
        this.title = title;
        this.genre = genre;
        this.authors = authors;
    }

}
