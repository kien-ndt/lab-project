package demo.lab.api.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @PostMapping("/v1/user/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("username", loginRequest.username);
        httpServletRequest.setAttribute("password", loginRequest.password);
        return "forward:/login";
    }

}
