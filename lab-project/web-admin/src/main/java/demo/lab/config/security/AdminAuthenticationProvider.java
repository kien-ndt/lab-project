package demo.lab.config.security;

import demo.lab.db.entity.AccountEntity;
import demo.lab.db.repository.AccountsRepository;
import demo.lab.security.authentication.AbstractUserPassAuthenticationProvider;
import demo.lab.security.authentication.UserPrincipalDetails;
import demo.lab.security.authorization.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAuthenticationProvider extends AbstractUserPassAuthenticationProvider {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsernamePasswordAuthenticationToken authenticateUserDetails(String username, String password) {
        AccountEntity accountEntity =
                accountsRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("Not found"));
        if (!passwordEncoder.matches(password, accountEntity.password)) {
            throw new BadCredentialsException("Not found");
        }
        if (accountEntity.role != Role.ADMIN) {
            throw new BadCredentialsException("Not found");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = List.of(new SimpleGrantedAuthority(accountEntity.role.name()));

        UserPrincipalDetails userPrincipalDetails = new UserPrincipalDetails();
        userPrincipalDetails.email = username;

        return new UsernamePasswordAuthenticationToken(userPrincipalDetails, password, simpleGrantedAuthorityList);
    }

}
