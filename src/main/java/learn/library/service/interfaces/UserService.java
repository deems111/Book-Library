package learn.library.service.interfaces;

import learn.library.entity.Role;
import learn.library.entity.User;
import learn.library.entity.enums.ERole;

/**
 * Interface for library service
 */
public interface UserService {

    User addUser(User user);

    User getUserByName(String name);

    Role getRoleByName(ERole eRole);
}
