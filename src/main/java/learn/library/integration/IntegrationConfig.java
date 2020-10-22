package learn.library.integration;

import learn.library.entity.Book;
import learn.library.entity.dto.BookDto;
import learn.library.entity.mappers.interfaces.BookMapper;
import learn.library.service.LibraryImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@AllArgsConstructor
@EnableIntegration
public class IntegrationConfig {

    private final BookMapper bookMapper;
    private final LibraryImpl libraryImpl;

    private final static String SERVICE_NAME = "anotherService";

    @Bean
    public MessageChannel inputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel outputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel saveChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel convertChannel() {
        return MessageChannels.direct().get();
    }

    public boolean route(Object o) {
        return o instanceof Book;
    }


    @Bean
    public IntegrationFlow saveBookFlow() {
        return IntegrationFlows.from("inputChannel")
                .log("inputChannel - enter")
                .handle(SERVICE_NAME, "doSomething")
                .<Object, Object>route(this::route,
                        mapping -> mapping
                                .channelMapping(true, "saveChannel")
                                .channelMapping(false, "convertChannel"))
                .get();
    }

    @Bean
    public IntegrationFlow convertFlow() {
        return IntegrationFlows.from("convertChannel")
                .log("convertChannel - enter")
                .transform(f -> bookMapper.toEntity((BookDto) f))
                .channel("saveChannel")
                .get();
    }

    @Bean
    public IntegrationFlow saveFlow() {
        return IntegrationFlows.from("saveChannel")
                .log("saveChannel - enter")
                .handle("libraryImpl", "addBook")
                .get();
    }

}
