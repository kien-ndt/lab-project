package demo.lab.security.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class AbstractUserPassAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public abstract void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException exception) throws IOException, ServletException;
}
