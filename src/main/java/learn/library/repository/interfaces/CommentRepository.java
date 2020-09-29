package learn.library.repository.interfaces;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Mono<Void> deleteByBookId(String bookId);

    Flux<Comment> findCommentsByBook(Mono<Book> book);

}
