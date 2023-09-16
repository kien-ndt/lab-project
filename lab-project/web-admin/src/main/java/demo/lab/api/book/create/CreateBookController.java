package demo.lab.api.book.create;

import demo.lab.api.book.create.model.CreateBookRequest;
import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateBookController {

    @Autowired
    private CreateBooksService createBooksService;

    @PostMapping("/v1/admin/book")
    public ResponseEntity<GenericResponse> createBookController(@RequestBody CreateBookRequest createBookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createBooksService.createBook(createBookRequest));
    }

}
