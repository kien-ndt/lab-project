package demo.lab.job.syncdata.step.reader;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
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
public class SyncDataJobReader extends ItemStreamSupport implements ItemReader<BookEntity> {

    @Autowired
    private BooksRepository booksRepository;

    private Iterator<BookEntity> iterator;
    @Value("#{jobParameters['ExecuteTime']}")
    private Long executeTime;
    @Override
    public void open(ExecutionContext executionContext) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(executeTime), TimeZone.getDefault().toZoneId());
//        iterator = booksRepository.findByUpdatedAtBetween(timeStart, now).iterator();
        iterator = booksRepository.findByUpdatedAtBefore(now).iterator();
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
