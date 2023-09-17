package demo.lab.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.lab.model.GenericResponse;
import demo.lab.security.authentication.AbstractUserPassAuthenticationSuccessHandler;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Service
public class UserAuthenticationSuccessHandler extends AbstractUserPassAuthenticationSuccessHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        GenericResponse genericResponse = new GenericResponse("Đăng nhập thành công");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(genericResponse));
        out.flush();
    }
}
