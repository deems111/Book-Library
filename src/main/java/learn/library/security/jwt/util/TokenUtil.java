package learn.library.security.jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Utility Class for Token Validity
 */
@Service
public class TokenUtil {

    private static final long serialVersionUID = 666L;
    private static final long TOKEN_VALIDITY = 180000;
    private static final String SALT = "NOT_TOO_SHORT";

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getSubject();
    }

    public String getAudienceFromToken(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getAudience();
    }

    private Date getExpirationFromToken(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getExpiration();
    }

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis() + 1000))
                .signWith(SignatureAlgorithm.HS512, SALT).compact();
    }

    public Boolean isExpired(String token) {
        return getExpirationFromToken(token).compareTo(new Date()) > 0;
    }

    public String getSalt() {
        return SALT;
    }

    public int getSaltLength() {
        return SALT.length();
    }

    public long getValidityTime() {
        return TOKEN_VALIDITY;
    }

}
