package demo.lab.job.syncdata.step.processor;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.CategoriesRepository;
import demo.lab.elasticsearch.document.BookDocument;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncDataJobProcessor implements ItemProcessor<BookEntity, BookDocument> {

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public BookDocument process(BookEntity bookEntity) throws Exception {
        BookDocument bookDocument = new BookDocument();
        bookDocument.id = bookEntity.id;
        bookDocument.title = bookEntity.title;
        bookDocument.author = authorsRepository.findById(bookEntity.authorId).orElseThrow().name;
        bookDocument.category = categoriesRepository.findById(bookEntity.categoryId).orElseThrow().label;
        bookDocument.createdAt = bookEntity.createdAt;
        return bookDocument;
    }
}
