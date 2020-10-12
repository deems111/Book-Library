package learn.library.repository.interfaces;

import learn.library.entity.Role;
import learn.library.entity.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>  {

    Role findByName(ERole name);
}
