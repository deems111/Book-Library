package learn.library.repository.interfaces;

import learn.library.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByAuthors(String author);

    List<Book> findByTitle(String title);

    void deleteById(String id);

}
