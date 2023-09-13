package demo.lab.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.lab.model.GenericResponse;
import demo.lab.security.authentication.AbstractUserPassAuthenticationFailureHandler;
import demo.lab.security.authentication.AbstractUserPassAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Service
public class AdminAuthenticationFailureHandler extends AbstractUserPassAuthenticationFailureHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException exception) throws IOException {
        GenericResponse genericResponse = new GenericResponse("Sai tên đăng nhập hoặc mật khẩu");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(genericResponse));
        out.flush();
    }
}
