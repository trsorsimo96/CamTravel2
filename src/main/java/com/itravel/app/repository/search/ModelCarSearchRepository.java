package com.itravel.app.repository.search;

import com.itravel.app.domain.ModelCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ModelCar entity.
 */
public interface ModelCarSearchRepository extends ElasticsearchRepository<ModelCar, Long> {
}
