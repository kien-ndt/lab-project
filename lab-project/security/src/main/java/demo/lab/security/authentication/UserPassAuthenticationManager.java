package demo.lab.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class UserPassAuthenticationManager implements AuthenticationManager {

    @Autowired
    private AbstractUserPassAuthenticationProvider userPassAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (userPassAuthenticationProvider.supports(authentication.getClass())) {
            return userPassAuthenticationProvider.authenticate(authentication);
        } else {
            return null;
        }
    }

}
