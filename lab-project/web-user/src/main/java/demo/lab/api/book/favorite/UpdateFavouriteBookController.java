package demo.lab.api.book.favorite;

import demo.lab.model.GenericResponse;
import demo.lab.security.authentication.UserPrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateFavouriteBookController {

    @Autowired
    private UpdateFavouriteBookService updateFavouriteBookService;

    @PostMapping(value = "/v1/book/{id}/update_favourite")
    public ResponseEntity<GenericResponse> updateFavouriteBook(
            @PathVariable("id") Integer id,
            @AuthenticationPrincipal UserPrincipalDetails userPrincipalDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(updateFavouriteBookService.updateFavouriteBook(id,
                userPrincipalDetails));
    }


}
