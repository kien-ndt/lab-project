package demo.lab.config.security;

import demo.lab.security.GeneralSecurityConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminSecurityConfig implements GeneralSecurityConfig {
    @Override
    public String[] getAllowEndpoints() {
        return new String[]{"/v1/admin/login", "/v1/admin/logout"};
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
