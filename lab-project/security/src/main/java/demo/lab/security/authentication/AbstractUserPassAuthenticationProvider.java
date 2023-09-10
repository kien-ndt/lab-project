package demo.lab.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

abstract public class AbstractUserPassAuthenticationProvider {

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        UserPrincipalDetails userPrincipalDetails = (UserPrincipalDetails) authenticationToken.getPrincipal();
        return authenticateUserDetails(userPrincipalDetails.email, (String) authenticationToken.getCredentials());
    };

    public boolean supports(Class<?> authentication) {
        if (Objects.equals(authentication,UsernamePasswordAuthenticationToken.class)) {
            return true;
        } else {
            return false;
        }
    };

    public abstract UsernamePasswordAuthenticationToken authenticateUserDetails(String username, String password);
}
