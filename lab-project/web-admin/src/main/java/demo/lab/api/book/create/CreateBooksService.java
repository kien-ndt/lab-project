package demo.lab.api.book.create;

import demo.lab.api.book.create.model.CreateBookRequest;
import demo.lab.db.entity.AuthorEntity;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.CategoryEntity;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.BooksRepository;
import demo.lab.db.repository.CategoriesRepository;
import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateBooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Transactional
    public GenericResponse createBook(CreateBookRequest createBookRequest) {
        AuthorEntity authorEntity = authorsRepository.findByName(createBookRequest.author).orElseGet(() -> {
            AuthorEntity newAuthorEntity = new AuthorEntity();
            newAuthorEntity.name = createBookRequest.author;
            return authorsRepository.save(newAuthorEntity);
        });

        CategoryEntity categoryEntity = categoriesRepository.findByLabel(createBookRequest.category).orElseGet(() -> {
            CategoryEntity newCategoryEntity = new CategoryEntity();
            newCategoryEntity.label = createBookRequest.category;
            return categoriesRepository.save(newCategoryEntity);
        });

        BookEntity bookEntity = new BookEntity();
        bookEntity.title = createBookRequest.title;
        bookEntity.authorId = authorEntity.id;
        bookEntity.categoryId = categoryEntity.id;
        bookEntity.createdAt = LocalDateTime.now();
        bookEntity.updatedAt = LocalDateTime.now();
        booksRepository.save(bookEntity);
        GenericResponse genericResponse = new GenericResponse("Thêm mới thành công");
        return genericResponse;
    }

}
