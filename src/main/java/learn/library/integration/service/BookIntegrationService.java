package learn.library.integration.service;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BookIntegrationService {

    public Object createBook() {
        int i = new Random().nextInt(3);
        if (i % 3 == 0) {
            i++;
            return makeBook(i);
        }
        return makeBookDto(i);
    }

    private BookDto makeBookDto(int i) {
        return new BookDto(null,"Integrated Author" + i, "title " + i, "genre " + i);
    }

    private Book makeBook(int i) {
        List<String> authors = new ArrayList<>(3);
        authors.add("Integrated Author " + i);
        return new Book("title " + i, "genre " + i, authors);
    }
}
