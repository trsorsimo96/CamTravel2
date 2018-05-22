package com.itravel.app.repository.search;

import com.itravel.app.domain.TypePassenger;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypePassenger entity.
 */
public interface TypePassengerSearchRepository extends ElasticsearchRepository<TypePassenger, Long> {
}
