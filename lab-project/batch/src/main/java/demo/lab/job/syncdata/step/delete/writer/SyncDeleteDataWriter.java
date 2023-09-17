package demo.lab.job.syncdata.step.delete.writer;

import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.elasticsearch.repository.BooksElasticSearchRepository;
import demo.lab.model.SqlExecutor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncDeleteDataWriter implements ItemWriter<SqlExecutor> {
    @Override
    public void write(List<? extends SqlExecutor> list) throws Exception {
        list.stream().forEach(SqlExecutor::execute);
    }
}
