package demo.lab.api.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginControllerTest {

    @PostMapping("/login")
    public String login() {
        return "asdasdasdasd";
    }

}
