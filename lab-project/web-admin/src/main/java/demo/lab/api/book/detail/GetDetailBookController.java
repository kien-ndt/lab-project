package demo.lab.api.book.detail;

import demo.lab.api.book.detail.model.GetDetailBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetDetailBookController {

    @Autowired
    private GetDetailBookService getDetailBookService;

    @GetMapping("/v1/admin/book/{id}")
    public ResponseEntity<GetDetailBookResponse> getDetailBookController(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(getDetailBookService.getDetailBook(id));
    }

}
