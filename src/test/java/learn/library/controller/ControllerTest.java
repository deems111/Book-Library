package learn.library.controller;

import learn.library.entity.Book;
import learn.library.repository.interfaces.BookRepository;
import learn.library.repository.interfaces.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebFluxTest(Controller.class)
public class ControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private WebTestClient webTestClient;

    private static final String TEST_AUTHOR = "Test_author_name_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";
    private static final String TEST_ID = "TEST_ID";

    @Test
    public void testGetAllBooks() {
        webTestClient.get().uri("/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType("application/json");
    }

    @Test
    public void testGetBooksById() {
        when(bookRepository.findById(TEST_ID)).thenReturn(Mono.empty());

        webTestClient.get().uri("/books/" + TEST_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType("application/json");
    }

    @Test
    public void testSaveBook() {
        given(bookRepository.findById("Q"))
                .willReturn(Mono.just(Book.builder().id("Q")
                        .title(TEST_BOOK_TITLE)
                        .genre(TEST_GENRE_NAME)
                        .build()));

        webTestClient.get()
                .uri("/books/Q")
                .exchange()
                .expectStatus()
                .isOk()
        .expectBody()
        .jsonPath("title").isEqualTo(TEST_BOOK_TITLE)
                .jsonPath("genre").isEqualTo(TEST_GENRE_NAME);
    }

}