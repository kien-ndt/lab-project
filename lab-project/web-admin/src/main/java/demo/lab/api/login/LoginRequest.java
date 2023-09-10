package demo.lab.api.login;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull
    public String username;
    @NotNull
    public String password;

}
