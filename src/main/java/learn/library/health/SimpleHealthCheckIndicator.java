package learn.library.health;

import learn.library.repository.interfaces.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SimpleHealthCheckIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        int bookCount = bookRepository.findAll().size();
        try {
            if (bookCount == 0) {
                throw new Exception("No books Found");
            }
            if (bookCount % 2 == 0) {
                return Health
                        .up()
                        .withDetail("message", "Book Size Is Even")
                        .build();
            }
            return Health
                    .down()
                    .withDetail("message", "Book Size Is Even")
                    .build();
        } catch (Exception e) {
            return Health
                    .down(e)
                    .withDetail("exception", e.getMessage())
                    .build();
        }
    }
}
