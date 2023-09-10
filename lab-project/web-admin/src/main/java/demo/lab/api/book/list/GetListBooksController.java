package demo.lab.api.book.list;

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
    private BooksElasticSearchRepository booksElasticSearchRepository;

    @GetMapping("/v1/book")
    public ResponseEntity<List<BookDocument>> getListBooksController() {
        Iterator<BookDocument> bookDocumentIterator = booksElasticSearchRepository.findAll().iterator();
        List<BookDocument> bookDocumentList = new ArrayList<>();
        while (bookDocumentIterator.hasNext()) {
            bookDocumentList.add(bookDocumentIterator.next());
        }
        return ResponseEntity.ok(bookDocumentList);
    }

}
