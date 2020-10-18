package learn.library.entity.mappers.interfaces;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;
import learn.library.entity.splDbEntity.AuthorSqlDb;
import learn.library.entity.splDbEntity.BookSqlDb;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface BookMapper {
    default BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .authors(convertAuthorsArrayToString(book.getAuthors()))
                .build();
    }

    default Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .genre(bookDto.getGenre())
                .authors(convertAuthorsArrayToList(bookDto.getAuthors()))
                .build();
    }

    default Book toMongoEntity(BookSqlDb bookSqlDb) {
        return Book.builder().id(String.valueOf(bookSqlDb.getId()))
                .authors(bookSqlDb.getAuthors().stream().map(this::authorToMongoEntity).collect(Collectors.toList()))
                .genre(bookSqlDb.getGenre().getName())
                .title(bookSqlDb.getTitle())
                .build();
    }

    default String authorToMongoEntity(AuthorSqlDb authorSqlDb) {
        return authorSqlDb.getName() + " , " + authorSqlDb.getSurname();
    }

    /**
     * Method convert strings with Author's name and surname to Set<String>
     */
    private List<String> convertAuthorsArrayToList(String authorStr) {
        return Arrays.stream(authorStr.split(";")).map(String::trim).collect(Collectors.toList());
    }

    /**
     * Method convert strings with Author's name and surname to Set<String>
     */
    private String convertAuthorsArrayToString(List<String> authors) {
        if (authors == null || authors.isEmpty()) {
            return "";
        }
        return String.join(" ", authors);
    }

}
