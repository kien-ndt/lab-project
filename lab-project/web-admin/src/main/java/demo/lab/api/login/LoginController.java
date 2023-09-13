package demo.lab.api.login;

import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @PostMapping("/v1/admin/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        httpServletRequest.setAttribute("username", loginRequest.username);
        httpServletRequest.setAttribute("password", loginRequest.password);
        return "forward:/login";
    }

}
