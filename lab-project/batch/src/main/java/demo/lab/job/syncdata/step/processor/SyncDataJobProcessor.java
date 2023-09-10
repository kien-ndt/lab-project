package demo.lab.job.syncdata.step.processor;

import demo.lab.db.entity.BookEntity;
import demo.lab.elasticsearch.document.BookDocument;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SyncDataJobProcessor implements ItemProcessor<BookEntity, BookDocument> {
    @Override
    public BookDocument process(BookEntity bookEntity) throws Exception {
        BookDocument bookDocument = new BookDocument();
        bookDocument.id = bookEntity.id;
        bookDocument.title = bookEntity.title;
        return bookDocument;
    }
}
