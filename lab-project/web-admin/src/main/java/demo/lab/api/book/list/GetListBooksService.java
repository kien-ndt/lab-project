package demo.lab.api.book.list;

import demo.lab.api.book.list.db.GetListBooksRepository;
import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.api.book.list.model.GetListBooksResponse.BookResponseEntity;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetListBooksService {

    @Autowired
    private GetListBooksRepository getListBooksRepository;

    public GetListBooksResponse getListBooks() {
        GetListBooksResponse getListBooksResponse = new GetListBooksResponse();
        getListBooksResponse.bookList = getListBooksRepository.findAllBooks().stream().map(booksInterface -> {
            BookResponseEntity bookResponseEntity = new BookResponseEntity();
            bookResponseEntity.id = booksInterface.getId();
            bookResponseEntity.title = booksInterface.getTitle();
            bookResponseEntity.author = booksInterface.getAuthor();
            bookResponseEntity.category = booksInterface.getCategory();
            return bookResponseEntity;
        }).collect(Collectors.toList());
        return getListBooksResponse;
    }

}
