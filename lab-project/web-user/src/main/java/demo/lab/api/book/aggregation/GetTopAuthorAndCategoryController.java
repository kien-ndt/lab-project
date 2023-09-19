package demo.lab.api.book.aggregation;

import demo.lab.api.book.aggregation.model.GetTopAuthorAndCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetTopAuthorAndCategoryController {

    @Autowired
    private GetTopAuthorAndCategoryService getTopAuthorAndCategoryService;

    @GetMapping(value = "/v1/book/aggregation")
    public ResponseEntity<GetTopAuthorAndCategoryResponse> getTopAuthorAndCategory() {
        return ResponseEntity.ok(getTopAuthorAndCategoryService.getTopAuthorAndCategory());
    }

}
