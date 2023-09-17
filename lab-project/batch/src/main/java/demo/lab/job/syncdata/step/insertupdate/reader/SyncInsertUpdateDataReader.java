package demo.lab.job.syncdata.step.insertupdate.reader;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import demo.lab.elasticsearch.document.ManageUpdateDocument;
import demo.lab.elasticsearch.repository.ManageUpdateElasticSearchRepository;
import demo.lab.model.ManageDocumentEnum;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.TimeZone;

@StepScope
@Service
public class SyncInsertUpdateDataReader extends ItemStreamSupport implements ItemReader<BookEntity> {

    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private ManageUpdateElasticSearchRepository manageUpdateElasticSearchRepository;

    private Iterator<BookEntity> iterator;
    @Value("#{jobParameters['ExecuteTime']}")
    private Long executeTime;

    @Override
    public void open(ExecutionContext executionContext) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(executeTime),
                TimeZone.getDefault().toZoneId());
        ManageUpdateDocument manageUpdateDocument =
                manageUpdateElasticSearchRepository.findById(ManageDocumentEnum.BOOK.id).orElse(null);
        LocalDateTime startTime;
        if (manageUpdateDocument == null) {
            startTime = currentTime.minusYears(1l);
        } else {
            startTime = manageUpdateDocument.lastUpdatedAt;
        }

        iterator = booksRepository.findByUpdatedAtBetween(startTime, currentTime).iterator();
    }

    @Override
    public BookEntity read() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}
