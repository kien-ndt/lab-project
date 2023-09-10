package demo.lab.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface GeneralSecurityConfig {

    String[] getAllowEndpoints();

    PasswordEncoder getPasswordEncoder();

}
