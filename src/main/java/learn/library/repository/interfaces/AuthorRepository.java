package learn.library.repository.interfaces;

import learn.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findAuthorByNameAndSurname(String name, String surname);
}
