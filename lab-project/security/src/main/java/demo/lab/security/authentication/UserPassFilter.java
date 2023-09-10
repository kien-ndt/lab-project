package demo.lab.security.authentication;

import demo.lab.exception.GenericRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/login", dispatcherTypes = {DispatcherType.FORWARD})

//@Service
public class UserPassFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login");

    public UserPassFilter(UserPassAuthenticationManager userPassAuthenticationManager,
                          AbstractUserPassAuthenticationSuccessHandler successHandler,
                          AbstractUserPassAuthenticationFailureHandler failureHandlerHandler) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, userPassAuthenticationManager);
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandlerHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = (String) request.getAttribute("username");
        String password = (String) request.getAttribute("password");
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            throw new GenericRuntimeException(HttpStatus.NOT_FOUND, "Not found");
        }
        UserPrincipalDetails userPrincipalDetails = new UserPrincipalDetails();
        userPrincipalDetails.email = username;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPrincipalDetails,
                password);
        return this.getAuthenticationManager().authenticate(token);
    }
}
