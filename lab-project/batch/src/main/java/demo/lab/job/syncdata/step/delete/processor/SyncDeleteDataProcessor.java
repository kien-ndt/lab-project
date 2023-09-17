package demo.lab.job.syncdata.step.delete.processor;

import demo.lab.db.entity.BookEntity;
import demo.lab.db.entity.DeletedBookEntity;
import demo.lab.db.repository.AuthorsRepository;
import demo.lab.db.repository.CategoriesRepository;
import demo.lab.db.repository.DeletedBooksRepository;
import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.elasticsearch.repository.BooksElasticSearchRepository;
import demo.lab.model.SqlExecutor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncDeleteDataProcessor implements ItemProcessor<DeletedBookEntity, SqlExecutor> {

    @Autowired
    private BooksElasticSearchRepository booksElasticSearchRepository;

    @Autowired
    private DeletedBooksRepository deletedBooksRepository;

    @Override
    public SqlExecutor process(DeletedBookEntity deletedBookEntity) {
        return () -> {
            booksElasticSearchRepository.deleteById(deletedBookEntity.bookId);
            deletedBooksRepository.deleteById(deletedBookEntity.id);
        };
    }
}
