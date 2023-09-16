package demo.lab.api.book.delete;

import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteBookController {

    @Autowired
    private DeleteBookService deleteBookService;

    @DeleteMapping("/v1/admin/book/{id}")
    public ResponseEntity<GenericResponse> deleteBook(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(deleteBookService.deleteBook(id));
    }

}
