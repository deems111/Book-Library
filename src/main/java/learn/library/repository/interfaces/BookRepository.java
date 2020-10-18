package learn.library.repository.interfaces;

import learn.library.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByAuthors(String author);

    List<Book> findByTitle(String title);

    List<Book> findByTitleRegex(String title);

}
