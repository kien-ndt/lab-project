package demo.lab.api.book.search;

import demo.lab.api.book.search.model.SearchBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchBookController {

    @Autowired
    private SearchBookService searchBookService;

    @GetMapping("/v1/admin/book/search")
    public ResponseEntity<SearchBookResponse> searchBook(@RequestParam(name = "q", required = false) String queryText) {
        return ResponseEntity.ok(searchBookService.searchBook(queryText != null ? queryText.trim() : ""));
    }

}
