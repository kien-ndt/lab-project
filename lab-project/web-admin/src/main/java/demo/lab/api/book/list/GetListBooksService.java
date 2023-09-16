package demo.lab.api.book.list;

import demo.lab.api.book.list.db.GetListBooksInterface;
import demo.lab.api.book.list.db.GetListBooksRepository;
import demo.lab.api.book.list.model.GetListBooksResponse;
import demo.lab.api.book.list.model.GetListBooksResponse.BookResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetListBooksService {

    @Autowired
    private GetListBooksRepository getListBooksRepository;

    @Autowired
    private SearchBookService searchBookService;

    public GetListBooksResponse getListBooks(String search) {
        GetListBooksResponse getListBooksResponse = new GetListBooksResponse();
        List<GetListBooksInterface> allBooks;
        if (!StringUtils.hasText(search)) {
            allBooks = getListBooksRepository.findAllBooks();
        } else {
            List<Integer> idBookList = searchBookService.searchBook(search.trim());
            List<GetListBooksInterface> bookEntityList = getListBooksRepository.findAllBooksByIdIn(idBookList);
            allBooks = new ArrayList<>();
            idBookList.forEach(id -> {
                Optional<GetListBooksInterface> targetEntity =
                        bookEntityList.stream().filter(getListBooksInterface -> Objects.equals(getListBooksInterface.getId(), id)).findFirst();
                if (targetEntity.isPresent()) {
                    allBooks.add(targetEntity.get());
                }
            });
        }
        getListBooksResponse.bookList = allBooks.stream().map(booksInterface -> {
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
