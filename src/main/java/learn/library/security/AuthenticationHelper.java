package learn.library.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Class Helper for Authentication
 */
@Service
@AllArgsConstructor
public class AuthenticationHelper {

    private final AuthenticationManager authenticationManager;

    //authenticate user of throw exception
    public void authenticateUser(String username, String password) throws SecurityException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new SecurityException("Invalid password or username " + e.getMessage());
        }
    }
}