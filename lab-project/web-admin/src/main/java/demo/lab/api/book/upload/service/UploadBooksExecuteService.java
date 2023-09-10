package demo.lab.api.book.upload.service;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadBooksExecuteService {

    @Autowired
    private BooksRepository booksRepository;

    @Transactional
    public Integer saveBooks(List<List<String>> elementsList) {
        List<BookEntity> bookEntityList = elementsList.stream().map(element -> {
            try {
                BookEntity bookEntity = new BookEntity();
                bookEntity.code = element.get(0);
                bookEntity.title = element.get(1);
                bookEntity.author = element.get(2);
                bookEntity.yearOfPublic = Integer.parseInt(element.get(3));
                bookEntity.publisher = element.get(4);
                bookEntity.imgUrl = element.get(5);
                bookEntity.updatedAt = LocalDateTime.now();
                return bookEntity;
            } catch (Exception e) {
                return null;
            }
        }).filter(bookEntity -> bookEntity != null).collect(Collectors.toList());
        booksRepository.saveAll(bookEntityList);
        return bookEntityList.size();
    }

}
