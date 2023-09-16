package demo.lab.api.account.list;

import demo.lab.api.account.list.model.GetListAccountsResponse;
import demo.lab.api.account.list.model.GetListAccountsResponse.AccountResponse;
import demo.lab.db.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetListAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    public GetListAccountsResponse getListAccounts() {
        GetListAccountsResponse getListAccountsResponse = new GetListAccountsResponse();
        getListAccountsResponse.accountList = accountsRepository.findAll().stream().filter(
                accountEntity -> !Objects.equals(accountEntity.email, "admin@mail.com")).map(accountEntity -> {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.id = accountEntity.id;
            accountResponse.email = accountEntity.email;
            accountResponse.role = accountEntity.role.name();
            return accountResponse;
        }).collect(Collectors.toList());
        return getListAccountsResponse;
    }

}
