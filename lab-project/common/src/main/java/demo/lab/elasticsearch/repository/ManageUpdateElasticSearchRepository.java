package demo.lab.elasticsearch.repository;

import demo.lab.elasticsearch.document.BookDocument;
import demo.lab.elasticsearch.document.ManageUpdateDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ManageUpdateElasticSearchRepository extends ElasticsearchRepository<ManageUpdateDocument, Integer> {

}
