package learn.library.repository.interfaces;

import learn.library.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g FROM Genre g WHERE g.name = :genreName")
    Genre getGenre(@Param(value = "genreName") String genre);
}
