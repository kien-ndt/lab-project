package demo.lab.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class AbstractUserPassAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public abstract void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                 Authentication authentication) throws IOException, ServletException;

}
