package demo.lab.api.book.list;

import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.security.authentication.UserPrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetListBooksController {

    @Autowired
    private GetListBooksService getListBooksService;

    @GetMapping("/v1/book")
    public ResponseEntity<GetListBooksResponse> getListBooksController(
            @RequestParam(required = false, name = "search") String search,
            @AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails) {
        return ResponseEntity.ok(getListBooksService.getListBooks(search, userPrincipalDetails));
    }

}
