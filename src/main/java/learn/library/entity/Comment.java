package learn.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
@Builder
public class Comment {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String subject;
    @DBRef
    private Book book;

    public Comment(String name, String subject, Book book) {
        this.name = name;
        this.subject = subject;
        this.book = book;
    }

}
