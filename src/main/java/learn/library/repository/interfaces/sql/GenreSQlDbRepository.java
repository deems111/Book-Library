package learn.library.repository.interfaces.sql;

import learn.library.entity.splDbEntity.GenreSqlDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreSQlDbRepository extends JpaRepository<GenreSqlDb, Long> {
}
