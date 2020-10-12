package learn.library.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @EqualsAndHashCode.Exclude
    private String id;
    @Field
    @Indexed(unique=true)
    private String username;
    @Field
    private String password;
    @DBRef
    private Set<Role> roles;

    public User (String name, String password, Set<Role> roles) {
        this.username = name;
        this.password = password;
        this.roles = roles;
    }

}
