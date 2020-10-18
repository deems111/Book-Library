package configuration;

import fillData.FillData;
import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import learn.library.batch.JobConfiguration;
import learn.library.batch.StepConfiguration;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class Config {

    @Bean
    public SpringMongock mongock(MongoTemplate mongoTemplate) {
        return new SpringMongockBuilder(mongoTemplate, FillData.class.getPackageName()).setLockQuickConfig().build();
    }

}
