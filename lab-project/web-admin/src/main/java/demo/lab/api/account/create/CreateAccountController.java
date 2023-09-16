package demo.lab.api.account.create;

import demo.lab.api.account.create.model.CreateAccountRequest;
import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateAccountController {

    @Autowired
    private CreateAccountService createAccountService;

    @PostMapping("/v1/admin/account")
    public ResponseEntity<GenericResponse> createAccountController(@RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createAccountService.createAccount(createAccountRequest));
    }

}
