package com.itravel.app.repository.search;

import com.itravel.app.domain.TypeVoyage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeVoyage entity.
 */
public interface TypeVoyageSearchRepository extends ElasticsearchRepository<TypeVoyage, Long> {
}
