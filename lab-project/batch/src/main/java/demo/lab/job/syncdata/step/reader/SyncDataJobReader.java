package demo.lab.job.syncdata.step.reader;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.repository.BooksRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;

@Service
public class SyncDataJobReader extends ItemStreamSupport implements ItemReader<BookEntity> {

    @Autowired
    private BooksRepository booksRepository;

    private Iterator<BookEntity> iterator;

    @Override
    public void open(ExecutionContext executionContext) {
        iterator = booksRepository.findByUpdatedAtBefore(LocalDateTime.now()).iterator();
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
