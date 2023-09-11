package demo.lab.api.book.list;

import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.elasticsearch.repository.BooksElasticSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class GetListBooksController {

    @Autowired
    private GetListBooksService getListBooksService;

    @GetMapping("/v1/admin/book")
    public ResponseEntity<GetListBooksResponse> getListBooksController() {
        return ResponseEntity.ok(getListBooksService.getListBooks());
    }

}
