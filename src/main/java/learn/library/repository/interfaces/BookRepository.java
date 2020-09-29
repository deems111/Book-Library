package learn.library.repository.interfaces;

import learn.library.entity.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByAuthors(String author);

    Flux<Book> findByTitle(String title);

}
