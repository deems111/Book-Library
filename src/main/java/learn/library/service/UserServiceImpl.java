package learn.library.service;

import learn.library.entity.Role;
import learn.library.entity.User;
import learn.library.entity.enums.ERole;
import learn.library.repository.interfaces.RoleRepository;
import learn.library.repository.interfaces.UserRepository;
import learn.library.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role getRoleByName(ERole eRole) {
        return roleRepository.findByName(eRole);
    }
}
