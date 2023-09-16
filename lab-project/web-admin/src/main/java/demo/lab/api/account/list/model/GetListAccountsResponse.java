package demo.lab.api.account.list.model;

import java.util.List;

public class GetListAccountsResponse {

    public List<AccountResponse> accountList;

    public static class AccountResponse {
        public Integer id;
        public String email;
        public String role;
    }

}
