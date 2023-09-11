package demo.lab.api.book.list;

import demo.lab.api.book.list.db.GetListBooksRepository;
import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListBooksService {

    @Autowired
    private GetListBooksRepository getListBooksRepository;

    public GetListBooksResponse getListBooks() {
        GetListBooksResponse getListBooksResponse = new GetListBooksResponse();
        getListBooksResponse.bookList = getListBooksRepository.findAllBooks();
        return getListBooksResponse;
    }

}
