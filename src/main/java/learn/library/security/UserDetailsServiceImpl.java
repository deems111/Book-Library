package learn.library.security;

import learn.library.entity.User;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Library libraryService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = libraryService.getUserByName(name);
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getRoles(), user.getPassword());
    }

}
