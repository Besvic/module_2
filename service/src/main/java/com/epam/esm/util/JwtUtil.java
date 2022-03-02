package com.epam.esm.util;

import com.epam.esm.security.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The type Jwt util.
 */
@Component
@Log4j2
public class JwtUtil {

    @Value("${jwt.token.key.word}")
    private String keyWord;

    @Value("${jwt.token.time}")
    private int milliseconds;


    /**
     * Generate jwt token string.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + milliseconds))
                .signWith(SignatureAlgorithm.HS512, keyWord).compact();
    }

    /**
     * Validate jwt token boolean.
     *
     * @param jwt the jwt
     * @return the boolean
     */
    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(keyWord).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * Gets user name from jwt token.
     *
     * @param jwt the jwt
     * @return the user name from jwt token
     */
    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(keyWord).parseClaimsJws(jwt).getBody().getSubject();
    }

}
