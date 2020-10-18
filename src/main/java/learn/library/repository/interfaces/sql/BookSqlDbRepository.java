package learn.library.repository.interfaces.sql;

import learn.library.entity.splDbEntity.BookSqlDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSqlDbRepository extends JpaRepository<BookSqlDb, Long> {
}
