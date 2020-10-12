package learn.library.controller;

import learn.library.entity.Role;
import learn.library.entity.User;
import learn.library.entity.enums.ERole;
import learn.library.repository.interfaces.RoleRepository;
import learn.library.security.AuthenticationHelper;
import learn.library.security.UserDetailsImpl;
import learn.library.security.UserDetailsServiceImpl;
import learn.library.security.jwt.login.jwt.TokenResponse;
import learn.library.security.jwt.login.request.AuthenticationRequest;
import learn.library.security.jwt.login.response.AuthenticationResponse;
import learn.library.security.jwt.login.response.SimpleResponse;
import learn.library.security.jwt.util.TokenUtil;
import learn.library.service.interfaces.Library;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SecurityController {

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenUtil tokenUtil;
    private final AuthenticationHelper authenticationHelper;
    private final PasswordEncoder encode;
    private final Library libraryService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws SecurityException {
        authenticationHelper.makeAuthentication(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new TokenResponse(token, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            validate(authenticationRequest);
        } catch (Exception e) {
            return ResponseEntity.ok(new SimpleResponse(e.getMessage()));
        }
        String requestRoles = authenticationRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (requestRoles == null || requestRoles.isEmpty()) {
            roles.add(libraryService.getRoleByName(ERole.ROLE_USER));
        } else {
            if (requestRoles.equalsIgnoreCase("admin")) {
                roles.add(libraryService.getRoleByName(ERole.ROLE_ADMIN));
            } else if (requestRoles.equalsIgnoreCase("user")) {
                roles.add(libraryService.getRoleByName(ERole.ROLE_USER));
            } else {
                roles.add(libraryService.getRoleByName(ERole.ROLE_ADMIN));
                roles.add(libraryService.getRoleByName(ERole.ROLE_USER));
            }

        }

        libraryService.addUser(new User(authenticationRequest.getUsername(), encode.encode(authenticationRequest.getPassword()),
                roles));

        return ResponseEntity.ok(new SimpleResponse("Success"));
    }

    private void validate(AuthenticationRequest authenticationRequest) throws Exception {
        if (authenticationRequest.getPassword().length() < 3) {
            throw new Exception("Password length Should Be > 3");
        }
        if (authenticationRequest.getUsername().length() < 3) {
            throw new Exception("User name length Should Be > 3");
        }
    }

}
