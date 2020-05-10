package learn.library.repository.interfaces;

import learn.library.entity.Genre;

public interface GenreDao {

    long addGenre(Genre genre);

    void deleteGenre(long id);

    Genre getGenre(String genre);

}
