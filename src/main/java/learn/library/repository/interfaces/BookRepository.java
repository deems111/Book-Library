package learn.library.repository.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors(Author authors);

    List<Book> findByTitle(String title);

}
