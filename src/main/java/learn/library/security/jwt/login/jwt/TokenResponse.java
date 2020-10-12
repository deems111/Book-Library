package learn.library.security.jwt.login.jwt;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 666L;
    private String token;
    private String id;
    private String username;
    private List<String> roles;

    public TokenResponse(String accessToken, List<String> roles) {
        this.token = accessToken;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }


}
