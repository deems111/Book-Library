package learn.library.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step migrateBookStep;
    private final Step migrateCommentStep;

    @Bean
    public Job migrationJob() {
        return jobBuilderFactory
                .get("migrateToMongo")
                .incrementer(new RunIdIncrementer())
                .flow(migrateBookStep)
                .from(migrateCommentStep)
                .end()
                .build();
    }
}
