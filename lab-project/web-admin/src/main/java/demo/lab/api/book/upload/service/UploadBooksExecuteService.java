package demo.lab.api.book.upload.service;

import demo.lab.api.book.upload.model.BookReaderEntity;
import demo.lab.db.entity.AuthorEntity;
import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.CategoryEntity;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.BooksRepository;
import demo.lab.db.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UploadBooksExecuteService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Transactional
    public Integer saveBooks(List<List<String>> elementsList) {

        List<BookReaderEntity> bookReaderEntityList = elementsList.stream().map(element -> {
            try {
                BookReaderEntity bookReaderEntity = new BookReaderEntity();
                bookReaderEntity.title = element.get(1);
                bookReaderEntity.author = element.get(2);
                bookReaderEntity.category = element.get(9);
                return bookReaderEntity;
            } catch (Exception e) {
                return null;
            }
        }).filter(bookReaderEntity -> bookReaderEntity != null).collect(Collectors.toList());

        List<String> authorNameList =
                bookReaderEntityList.stream().map(bookReaderEntity -> bookReaderEntity.author).filter(authorName -> StringUtils.hasLength(authorName)).collect(Collectors.toList());

        List<AuthorEntity> authorEntityList;
        if (!CollectionUtils.isEmpty(authorNameList)) {
            authorEntityList = authorsRepository.findByNameInList(authorNameList);
        } else {
            authorEntityList = new ArrayList<>();
        }

        List<String> unknownAuthorNameList =
                authorNameList.stream().filter(
                        authorName -> authorEntityList.stream().noneMatch(
                                authorEntity -> Objects.equals(authorEntity.name, authorName))).distinct().collect(Collectors.toList());
        List<AuthorEntity> newAuthorEntity =
                authorsRepository.saveAll(unknownAuthorNameList.stream().map(unknownAuthorName -> {
                    AuthorEntity authorEntity = new AuthorEntity();
                    authorEntity.name = unknownAuthorName;
                    return authorEntity;
                }).collect(Collectors.toList()));
        authorEntityList.addAll(newAuthorEntity);

        List<String> categoryLabelList =
                bookReaderEntityList.stream().map(
                                bookReaderEntity -> bookReaderEntity.category)
                        .filter(categoryName -> StringUtils.hasLength(categoryName)).collect(Collectors.toList());

        List<CategoryEntity> categoryEntityList;
        if (!CollectionUtils.isEmpty(categoryLabelList)) {
            categoryEntityList = categoriesRepository.findByLabelInList(categoryLabelList);
        } else {
            categoryEntityList = new ArrayList<>();
        }

        List<String> unknownCategoryLabelList =
                categoryLabelList.stream().filter(
                        categoryLabel -> categoryEntityList.stream().noneMatch(
                                categoryEntity -> Objects.equals(categoryEntity.label, categoryLabel))).distinct().collect(Collectors.toList());
        List<CategoryEntity> newCategoryEntity =
                categoriesRepository.saveAll(unknownCategoryLabelList.stream().map(unknownCategoryLabel -> {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.label = unknownCategoryLabel;
                    return categoryEntity;
                }).collect(Collectors.toList()));
        categoryEntityList.addAll(newCategoryEntity);

        LocalDateTime now = LocalDateTime.now();
        List<BookEntity> bookEntityList = bookReaderEntityList.stream().map(bookReaderEntity -> {
            BookEntity bookEntity = new BookEntity();
            bookEntity.title = bookReaderEntity.title;
            bookEntity.authorId = authorEntityList.stream().filter(authorEntity -> Objects.equals(authorEntity.name,
                    bookReaderEntity.author)).findAny().orElseThrow().id;
            bookEntity.categoryId =
                    categoryEntityList.stream().filter(categoryEntity -> Objects.equals(categoryEntity.label,
                            bookReaderEntity.category)).findAny().orElseThrow().id;
            bookEntity.updatedAt = now;
            bookEntity.createdAt = now;
            return bookEntity;
        }).collect(Collectors.toList());
        booksRepository.saveAll(bookEntityList);
        return bookEntityList.size();
    }

}
