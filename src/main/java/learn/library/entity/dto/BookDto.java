package learn.library.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookDto {

    private String id;
    private String authors;
    private String title;
    private String genre;

}
