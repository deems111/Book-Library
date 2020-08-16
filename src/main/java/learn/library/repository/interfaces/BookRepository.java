package learn.library.repository.interfaces;

import learn.library.entity.Author;
import learn.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors(Author authors);

    List<Book> findByTitle(String title);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Book b SET b.title = :title WHERE b.id = :id")
    int updateBookTitleById(@Param(value = "id") Long id, @Param(value = "title") String newTitle);

}
