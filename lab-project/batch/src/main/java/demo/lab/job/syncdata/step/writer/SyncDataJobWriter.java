package demo.lab.job.syncdata.step.writer;

import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.elasticsearch.repository.BooksElasticSearchRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncDataJobWriter implements ItemWriter<BookDocument> {

    @Autowired
    private BooksElasticSearchRepository booksElasticSearchRepository;

    @Override
    public void write(List<? extends BookDocument> list) throws Exception {
        list.stream().forEach(bookDocument -> booksElasticSearchRepository.save(bookDocument));
    }

//    @Override
//    public void write(Chunk<? extends BookDocument> chunk) throws Exception {
//        chunk.getItems().forEach(bookDocument -> booksElasticSearchRepository.save(bookDocument));
//    }
}
