package learn.library.repository.interfaces;

import learn.library.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>  {

    User findByUsername(String username);
}
