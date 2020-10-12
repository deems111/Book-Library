package learn.library.entity;

import learn.library.entity.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    @EqualsAndHashCode.Exclude
    private String id;
    @Field
    @Enumerated(EnumType.STRING)
    private ERole name;

}
