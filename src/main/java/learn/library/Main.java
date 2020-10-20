package learn.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableMongoRepositories
@EnableIntegration
@IntegrationComponentScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
