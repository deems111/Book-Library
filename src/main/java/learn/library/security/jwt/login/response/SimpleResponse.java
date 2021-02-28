package learn.library.security.jwt.login.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Auth Response to Show on UI
 */
@AllArgsConstructor
@Getter
@Setter
public class SimpleResponse {
    //Success when OK, otherwise - error message
    private String message;
}
