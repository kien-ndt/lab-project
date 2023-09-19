package demo.lab.job.syncdata.step.insertupdate.processor;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.CategoriesRepository;
import demo.lab.elasticsearch.document.BookDocument;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncInsertUpdateDataProcessor implements ItemProcessor<BookEntity, BookDocument> {

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public BookDocument process(BookEntity bookEntity) {
        BookDocument bookDocument = new BookDocument();
        bookDocument.id = bookEntity.id;
        bookDocument.title = bookEntity.title;
        bookDocument.author = authorsRepository.findById(bookEntity.authorId).orElseThrow().name;
        bookDocument.author_keyword = bookDocument.author;
        bookDocument.category = categoriesRepository.findById(bookEntity.categoryId).orElseThrow().label;
        bookDocument.category_keyword = bookDocument.category;
        bookDocument.createdAt = bookEntity.createdAt;
        return bookDocument;
    }
}
