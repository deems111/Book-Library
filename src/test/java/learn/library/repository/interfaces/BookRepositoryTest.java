package learn.library.repository.interfaces;

import learn.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static final String TEST_AUTHOR = "Test_author_name_surname";
    private static final String TEST_GENRE_NAME = "Test genre name";
    private static final String TEST_BOOK_TITLE = "Test book title";

    @BeforeEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    public void addBook() {

        bookRepository.save(createBook());

        Assert.isTrue(bookRepository.findAll().size() == 1, "Get books <> 1");
        Assert.isTrue(bookRepository.findByAuthors(TEST_AUTHOR).size() == 1, "Get book by author <> 1");
        Assert.isTrue(bookRepository.findByTitle(TEST_BOOK_TITLE).size() == 1, "Get book by title <> 1");
    }

    @Test
    public void deleteBook() {

        Book book = bookRepository.save(createBook());

        int sizeBeforeDelete = bookRepository.findAll().size();
        bookRepository.deleteById(book.getId());
        int sizeAfterDelete = bookRepository.findAll().size();

        Assert.isTrue(sizeBeforeDelete - sizeAfterDelete == 1, "Size difference is not 1");
        Assert.isTrue(bookRepository.findByTitle(TEST_BOOK_TITLE + "to delete").size() == 0, "Get book by title is not null");
        Assert.isTrue(commentRepository.findCommentsByBook(book).size() == 0, "Get book comments size is not 0");

    }

    @Test
    public void getBookByAuthorTest() throws Exception {

        Book book = bookRepository.save(createBook());

        Assert.isTrue(bookRepository.findByAuthors(TEST_AUTHOR).size() == 1, "Get book comments size is not 1");
        Assert.isTrue(bookRepository.findByAuthors(TEST_AUTHOR).get(0)
                .getAuthors().iterator().next().equalsIgnoreCase(TEST_AUTHOR), "Get book comments size is not 1");
    }

    private Book createBook() {
        Set<String> authors = new HashSet<>();
        authors.add(TEST_AUTHOR);
        Book book = new Book(TEST_BOOK_TITLE, TEST_GENRE_NAME, authors);

        return book;
    }

}
