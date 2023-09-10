package demo.lab.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

@Configuration
public class FilterConfig {

    @Autowired
    private UserPassAuthenticationManager userPassAuthenticationManager;
    @Autowired
    private AbstractUserPassAuthenticationSuccessHandler successHandler;
    @Autowired
    private AbstractUserPassAuthenticationFailureHandler failureHandlerHandler;

    @Bean
    public FilterRegistrationBean userPassFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UserPassFilter(userPassAuthenticationManager, successHandler, failureHandlerHandler));
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
//        registration.setOrder(1);
        registration.setDispatcherTypes(DispatcherType.FORWARD);
        return registration;
    }
}
