package learn.library.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationHelper {

    private final AuthenticationManager authenticationManager;

    public void makeAuthentication(String username, String password) throws SecurityException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new SecurityException("Invalid password or username " + e.getMessage());
        }
    }
}