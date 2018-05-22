package com.itravel.app.repository.search;

import com.itravel.app.domain.Routes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Routes entity.
 */
public interface RoutesSearchRepository extends ElasticsearchRepository<Routes, Long> {
}
