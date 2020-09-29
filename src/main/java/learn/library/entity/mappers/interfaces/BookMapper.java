package learn.library.entity.mappers.interfaces;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;

import java.util.Arrays;
import java.util.List;
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
