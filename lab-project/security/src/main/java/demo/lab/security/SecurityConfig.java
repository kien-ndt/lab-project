package demo.lab.security;

import demo.lab.security.authentication.HandleLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private GeneralSecurityConfig generalSecurityConfig;

    @Autowired
    private HandleLogoutSuccessHandler handleLogoutSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().requestCache().disable().cors().and()
                .authorizeRequests()
                .mvcMatchers(generalSecurityConfig.getAllowEndpoints()).permitAll()
                .mvcMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/**/**/logout", HttpMethod.GET.name()))
                .logoutSuccessHandler(handleLogoutSuccessHandler);
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return generalSecurityConfig.getPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
