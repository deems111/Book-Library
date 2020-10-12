package learn.library.db.config;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import learn.library.db.FillDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public SpringMongock mongock(MongoTemplate mongoTemplate) {
        return new SpringMongockBuilder(mongoTemplate, FillDatabase.class.getPackage().getName())
                .setLockQuickConfig()
                .build();
    }

}
