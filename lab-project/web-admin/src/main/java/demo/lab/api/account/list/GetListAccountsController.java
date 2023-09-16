package demo.lab.api.account.list;

import demo.lab.api.account.list.model.GetListAccountsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetListAccountsController {

    @Autowired
    private GetListAccountsService getListAccountsService;

    @GetMapping("/v1/admin/account")
    public ResponseEntity<GetListAccountsResponse> getListAccounts() {
        return ResponseEntity.ok(getListAccountsService.getListAccounts());
    }

}
