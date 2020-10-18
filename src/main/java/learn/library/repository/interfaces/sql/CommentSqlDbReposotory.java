package learn.library.repository.interfaces.sql;

import learn.library.entity.splDbEntity.CommentSqlDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentSqlDbReposotory extends JpaRepository<CommentSqlDb, Long> {
}
