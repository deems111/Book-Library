package learn.library.integration;

import learn.library.entity.Book;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface BookIntegrationGateway {

    @Gateway(requestChannel = "inputChannel")
    public Book process(Object o);

}
