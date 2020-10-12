package learn.library.db;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Role;
import learn.library.entity.User;
import learn.library.entity.enums.ERole;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ChangeLog
@AllArgsConstructor
public class FillDatabase {

    @ChangeSet(author = "deems111", id = "drop", order = "001")
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(author = "deems111", id = "fill", order = "002")
    public void fillDB(MongoTemplate mongoTemplate) {

        List<String> authors = new ArrayList<>(4);
        authors.add("Author Name");
        Book firstBook = mongoTemplate.save(new Book("First Book Title", "First Genre", authors));
        Book secondBook = mongoTemplate.save(new Book("Second Book Title", "First Genre", authors));
        authors.add("New Author Name");
        Book thirdBook = mongoTemplate.save(new Book("Third Book Title", "Third Genre", authors));

        Comment comment1Book = mongoTemplate.save(new Comment("Anonymous", "First Comment", firstBook));
        Comment comment2Book = mongoTemplate.save(new Comment("Author", "Just TypicalComment", secondBook));

        Role roleAdmin = mongoTemplate.save(new Role("admin", ERole.ROLE_ADMIN));
        Role roleUser = mongoTemplate.save(new Role("user", ERole.ROLE_USER));

        Set<Role> roles = new HashSet<>(4);
        roles.add(roleUser);
        User user = mongoTemplate.save(new User("user", "$2y$12$96x0wLv1LJbG1m7dxBbOy.5SYSVhuF8KeOSfmpWoMAQsQPRlSrhr6 ", roles));

        roles.add(roleAdmin);
        User admin = mongoTemplate.save(new User("admin", "$2y$12$96x0wLv1LJbG1m7dxBbOy.5SYSVhuF8KeOSfmpWoMAQsQPRlSrhr6", roles));

    }
}
