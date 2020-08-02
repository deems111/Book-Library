package FillData;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashSet;
import java.util.Set;

@ChangeLog(order = "1")
public class FillData {

    @ChangeSet(author = "", id = "initMongo", order ="1" )
    public void init(MongoTemplate mongoTemplate) {
        Set<String> authors = new HashSet<>();
        authors.add("AuthorName Surname1");
        authors.add("AuthorName Surname2");
        Book testBook = mongoTemplate.save(new Book("Mongo Test Book 1", "Mongo Test Genre 1", authors));
        authors.clear();
        authors.add("AuthorName2 Surname1");
        Book testBook2 = mongoTemplate.save(new Book("Mongo Test Book 2", "Mongo Test Genre 2", authors));
        authors.add("AuthorName3 Surname1");
        Book testBook3 = mongoTemplate.save(new Book("Mongo Test Book 3", "Mongo Test Genre 2", authors));

        mongoTemplate.save(new Comment("TestName","Test comment", testBook));
        mongoTemplate.save(new Comment("TestName2","Test comment2", testBook2));
    }

}
