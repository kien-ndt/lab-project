package demo.lab.api.book.update;

import demo.lab.api.book.update.model.UpdateBookRequest;
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
public class UpdateBooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Transactional
    public GenericResponse updateBook(UpdateBookRequest updateBookRequest) {
        BookEntity bookEntity = booksRepository.findById(updateBookRequest.id).orElseThrow();
        AuthorEntity authorEntity = authorsRepository.findByName(updateBookRequest.author).orElseGet(() -> {
            AuthorEntity newAuthorEntity = new AuthorEntity();
            newAuthorEntity.name = updateBookRequest.author;
            return authorsRepository.save(newAuthorEntity);
        });

        CategoryEntity categoryEntity = categoriesRepository.findByLabel(updateBookRequest.category).orElseGet(() -> {
            CategoryEntity newCategoryEntity = new CategoryEntity();
            newCategoryEntity.label = updateBookRequest.category;
            return categoriesRepository.save(newCategoryEntity);
        });
        bookEntity.title = updateBookRequest.title;
        bookEntity.authorId = authorEntity.id;
        bookEntity.categoryId = categoryEntity.id;
        bookEntity.updatedAt = LocalDateTime.now();
        booksRepository.save(bookEntity);
        GenericResponse genericResponse = new GenericResponse("Chỉnh sửa thành công");
        return genericResponse;
    }

}
