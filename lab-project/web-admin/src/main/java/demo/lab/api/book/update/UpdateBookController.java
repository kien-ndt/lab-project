package demo.lab.api.book.update;

import demo.lab.api.book.update.model.UpdateBookRequest;
import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateBookController {

    @Autowired
    private UpdateBooksService updateBooksService;

    @PutMapping("/v1/admin/book")
    public ResponseEntity<GenericResponse> createBookController(@RequestBody UpdateBookRequest createBookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(updateBooksService.updateBook(createBookRequest));
    }

}
