package demo.lab.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityConfig implements GeneralSecurityConfig {
    @Override
    public String[] getAllowEndpoints() {
        return new String[]{"/v1/user/login", "/v1/user/logout", "/v1/book"};
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
