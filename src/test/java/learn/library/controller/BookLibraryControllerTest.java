package learn.library.controller;

import learn.library.entity.Book;
import learn.library.security.UserDetailsServiceImpl;
import learn.library.security.jwt.login.jwt.JwtEntryPoint;
import learn.library.security.jwt.util.TokenUtil;
import learn.library.service.interfaces.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureDataMongo
@WebMvcTest(BookLibraryController.class)
@ExtendWith(SpringExtension.class)
public class BookLibraryControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Library libraryService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtEntryPoint jwtEntryPoint;
    @MockBean
    private TokenUtil tokenUtil;

    private Book book;

    @BeforeEach
    void setUp() {
        List<String> authors = new ArrayList<>();
        authors.add("Author");
        book = Book.builder()
                .id("111")
                .title("Test")
                .genre("Test Genre")
                .authors(authors)
                .build();
        libraryService.addBook(book);
    }

    @Test
    @WithMockUser(value = "user", roles = {"USER"})
    void testUserWithRights() throws Exception {
        given(libraryService.getBooks()).willReturn(List.of(book));
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}