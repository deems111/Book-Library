package learn.library.controller;

import learn.library.service.LibraryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryImpl library;

    @Test
    public void getBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    //test view not existing book
    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/1000000"))
                .andExpect(status().is4xxClientError());
    }

}