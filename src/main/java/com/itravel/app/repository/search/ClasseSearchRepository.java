package com.itravel.app.repository.search;

import com.itravel.app.domain.Classe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Classe entity.
 */
public interface ClasseSearchRepository extends ElasticsearchRepository<Classe, Long> {
}
