package demo.lab.job.syncdata.step.delete.reader;

import demo.lab.db.entity.DeletedBookEntity;
import demo.lab.db.repository.DeletedBooksRepository;
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
public class SyncDeleteDataReader extends ItemStreamSupport implements ItemReader<DeletedBookEntity> {

    @Autowired
    private DeletedBooksRepository deletedBooksRepository;
    @Autowired
    private ManageUpdateElasticSearchRepository manageUpdateElasticSearchRepository;

    private Iterator<DeletedBookEntity> iterator;
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
        iterator = deletedBooksRepository.findByDeletedAtBetween(startTime, currentTime).iterator();
    }

    @Override
    public DeletedBookEntity read() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}
