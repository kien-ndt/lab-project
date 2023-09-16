package demo.lab.api.account.create;

import demo.lab.api.account.create.model.CreateAccountRequest;
import demo.lab.db.entity.AccountEntity;
import demo.lab.db.repository.AccountsRepository;
import demo.lab.exception.GenericRuntimeException;
import demo.lab.model.GenericResponse;
import demo.lab.security.authorization.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CreateAccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public GenericResponse createAccount(CreateAccountRequest createAccountRequest) {

        if (accountsRepository.existsByEmail(createAccountRequest.email)) {
            throw new GenericRuntimeException("Email đã tồn tại trong hệ thống");
        }

        if (!Objects.equals(Role.ADMIN.name(), createAccountRequest.role) && !Objects.equals(Role.USER.name(), createAccountRequest.role)) {
            throw new GenericRuntimeException("Quyền hạn không hợp lệ");
        }

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.email = createAccountRequest.email;
        accountEntity.password = passwordEncoder.encode(createAccountRequest.password);
        accountEntity.role = Role.valueOf(createAccountRequest.role);
        accountsRepository.save(accountEntity);
        return new GenericResponse("Thêm tài khoản thành công");
    }

}
