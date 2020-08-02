package Configuration;

import FillData.FillData;
import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class Config {

    @Bean
    public SpringMongock mongock(MongoTemplate mongoTemplate) {
        return new SpringMongockBuilder(mongoTemplate, FillData.class.getPackageName()).setLockQuickConfig().build();
    }
}
