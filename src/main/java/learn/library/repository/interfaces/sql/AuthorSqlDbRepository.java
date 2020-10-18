package learn.library.repository.interfaces.sql;

import learn.library.entity.splDbEntity.AuthorSqlDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorSqlDbRepository extends JpaRepository<AuthorSqlDb, Long> {
}
