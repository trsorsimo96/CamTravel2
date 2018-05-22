package com.itravel.app.repository.search;

import com.itravel.app.domain.StateVoyage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StateVoyage entity.
 */
public interface StateVoyageSearchRepository extends ElasticsearchRepository<StateVoyage, Long> {
}
