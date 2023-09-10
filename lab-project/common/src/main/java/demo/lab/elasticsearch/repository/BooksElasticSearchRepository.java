package demo.lab.elasticsearch.repository;

import demo.lab.elasticsearch.document.BookDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BooksElasticSearchRepository extends ElasticsearchRepository<BookDocument, Integer> {

}
