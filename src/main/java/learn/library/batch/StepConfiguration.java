package learn.library.batch;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.mappers.interfaces.BookMapper;
import learn.library.entity.mappers.interfaces.CommentMapper;
import learn.library.entity.splDbEntity.BookSqlDb;
import learn.library.entity.splDbEntity.CommentSqlDb;
import learn.library.repository.interfaces.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
@AllArgsConstructor
public class StepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final EntityManagerFactory entityManagerFactory;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;
    private final BatchListener batchListener;

    private static final int SKIP_COUNT = 3;

    @Bean
    public <T> MongoItemWriter<T> writer(String collection) {
        return new MongoItemWriterBuilder<T>()
                .template(mongoTemplate)
                .collection(collection)
                .build();
    }



    @Bean
    public <T> JpaPagingItemReader<T> jdbcReader(String query) {
        return new JpaPagingItemReaderBuilder<T>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(30)
                .queryString(query)
                .build();
    }

    @Bean
    public ItemProcessor<BookSqlDb, Book> bookItemProcessor() {
        return bookMapper::toMongoEntity;
    }

    @Bean
    public ItemProcessor<CommentSqlDb, Comment> commentItemProcessor() {
        return commentMapper::toMongoEntity;
    }


    @Bean
    public Step migrateBookStep() {
        return stepBuilderFactory.get("migrateBookStep")
                .<BookSqlDb, Book>chunk(3)
                .reader(jdbcReader("select b from BookSqlDb b"))
                .processor(bookItemProcessor())
                .writer(writer("books"))
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(SKIP_COUNT)
                .listener(batchListener)
                .build();
    }

    @Bean
    public Step migrateCommentStep() {
        return stepBuilderFactory.get("migrateCommentStep")
                .<CommentSqlDb, Comment>chunk(3)
                .reader(jdbcReader("select c from CommentSqlDb c"))
                .processor(commentItemProcessor())
                .writer(writer("comments"))
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(SKIP_COUNT)
                .listener(batchListener)
                .build();
    }

}
