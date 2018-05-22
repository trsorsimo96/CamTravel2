package com.itravel.app.repository.search;

import com.itravel.app.domain.Voyage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Voyage entity.
 */
public interface VoyageSearchRepository extends ElasticsearchRepository<Voyage, Long> {
}
