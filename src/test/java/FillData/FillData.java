package FillData;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.repository.interfaces.BookRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "1")
public class FillData {

    @ChangeSet(author = "", id = "initMongo", order ="1" )
    public void init(BookRepository bookRepository) {
        List<String> authors = new ArrayList<>();
        authors.add("AuthorName Surname1");
        authors.add("AuthorName Surname2");
        bookRepository.save(new Book("Mongo Test Book 1", "Mongo Test Genre 1", authors));
        authors.clear();
        authors.add("AuthorName2 Surname1");
        bookRepository.save(new Book("Mongo Test Book 2", "Mongo Test Genre 2", authors));
        authors.add("AuthorName3 Surname1");
        bookRepository.save(new Book("Mongo Test Book 3", "Mongo Test Genre 2", authors));

    }

}
