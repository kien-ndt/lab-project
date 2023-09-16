package demo.lab.api.book.detail;

import demo.lab.api.book.detail.model.GetDetailBookResponse;
import demo.lab.api.book.list.db.GetListBooksRepository;
import demo.lab.api.book.list.model.GetListBooksResponse.BookResponseEntity;
import demo.lab.db.entity.AccountEntity;
import demo.lab.db.entity.AuthorEntity;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.CategoryEntity;
import demo.lab.db.repository.AccountsRepository;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.BooksRepository;
import demo.lab.db.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GetDetailBookService {

    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private AuthorsRepository authorsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;

    public GetDetailBookResponse getDetailBook(Integer id) {
        BookEntity bookEntity = booksRepository.findById(id).orElseThrow();
        AuthorEntity authorEntity = authorsRepository.findById(bookEntity.authorId).orElseThrow();
        CategoryEntity categoryEntity = categoriesRepository.findById(bookEntity.categoryId).orElseThrow();

        GetDetailBookResponse getListBooksResponse = new GetDetailBookResponse();
        getListBooksResponse.id = bookEntity.id;
        getListBooksResponse.title = bookEntity.title;
        getListBooksResponse.author = authorEntity.name;
        getListBooksResponse.category = categoryEntity.label;
        return getListBooksResponse;
    }

}
