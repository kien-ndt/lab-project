package demo.lab.api.book.delete;

import demo.lab.db.entity.*;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.BooksRepository;
import demo.lab.db.repository.CategoriesRepository;
import demo.lab.db.repository.DeletedBooksRepository;
import demo.lab.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeleteBookService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private DeletedBooksRepository deletedBooksRepository;

    @Transactional
    public GenericResponse deleteBook(Integer id) {
        BookEntity bookEntity = booksRepository.findByIdForUpdate(id).orElseThrow();
        AuthorEntity authorEntity = authorsRepository.findById(bookEntity.authorId).orElseThrow();
        CategoryEntity categoryEntity = categoriesRepository.findById(bookEntity.categoryId).orElseThrow();
        DeletedBookEntity deletedBookEntity = new DeletedBookEntity();
        deletedBookEntity.bookId = bookEntity.id;
        deletedBookEntity.deletedAt = LocalDateTime.now();
        booksRepository.delete(bookEntity);
        deletedBooksRepository.save(deletedBookEntity);

        if (!booksRepository.existsByAuthorId(authorEntity.id)) {
            authorsRepository.delete(authorEntity);
        }

        if (!booksRepository.existsByCategoryId(categoryEntity.id)) {
            categoriesRepository.delete(categoryEntity);
        }

        return new GenericResponse("Xóa thành công");
    }

}
