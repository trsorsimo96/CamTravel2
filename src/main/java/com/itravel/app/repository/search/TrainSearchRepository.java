package com.itravel.app.repository.search;

import com.itravel.app.domain.Train;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Train entity.
 */
public interface TrainSearchRepository extends ElasticsearchRepository<Train, Long> {
}
