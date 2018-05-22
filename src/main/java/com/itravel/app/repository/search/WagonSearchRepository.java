package com.itravel.app.repository.search;

import com.itravel.app.domain.Wagon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Wagon entity.
 */
public interface WagonSearchRepository extends ElasticsearchRepository<Wagon, Long> {
}
