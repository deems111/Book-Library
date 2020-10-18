package learn.library.batch;

import learn.library.entity.splDbEntity.AuthorSqlDb;
import learn.library.entity.splDbEntity.BookSqlDb;
import learn.library.entity.splDbEntity.GenreSqlDb;
import learn.library.repository.interfaces.BookRepository;
import learn.library.repository.interfaces.sql.AuthorSqlDbRepository;
import learn.library.repository.interfaces.sql.BookSqlDbRepository;
import learn.library.repository.interfaces.sql.CommentSqlDbReposotory;
import learn.library.repository.interfaces.sql.GenreSQlDbRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SpringBatchTest
@SpringBootTest
public class JobConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private MongoTemplate mongoTemplate;
    @MockBean
    private BookSqlDbRepository bookSqlDbRepository;
    @MockBean
    private AuthorSqlDbRepository authorSqlDbRepository;
    @Autowired
    private BookRepository bookRepository;
    @MockBean
    private CommentSqlDbReposotory commentSqlDbReposotory;

    @Test
    public void testLaunchJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job.getName(), is("migrateToMongo"));

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
    }

    @Test
    public void testLaunchJobResult() throws Exception {
        bookRepository.deleteAll();
        assertThat(bookRepository.findByTitleRegex("test").size(), is(0));
        jobLauncherTestUtils.launchJob(new JobParameters());
        assertThat(bookRepository.findById("1").isPresent(), is(true));
        assertThat(bookRepository.findById("2").isPresent(), is(true));
        assertThat(bookRepository.findById("3").isPresent(), is(true));
        assertThat(bookRepository.findByTitle("test First book title").size(), is(1));
        assertThat(bookRepository.findByTitle("test Second book title").size(), is(1));
        assertThat(bookRepository.findByTitle("test Third book title").size(), is(1));
        assertThat(bookRepository.findByTitleRegex("test").size(), is(3));
    }

    private List<BookSqlDb> getBooks() {
        Set<AuthorSqlDb> authorSqlDbs = new HashSet<>(3);
        authorSqlDbs.add(new AuthorSqlDb(10000L, "surname", "name"));
        BookSqlDb bookSqlDb = new BookSqlDb(10000L, "title",new GenreSqlDb(10000L, "GenreQWERTY"), authorSqlDbs);
        return List.of(bookSqlDb);
    }
}