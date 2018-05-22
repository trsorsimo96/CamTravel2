package com.itravel.app.repository.search;

import com.itravel.app.domain.CompanyClasse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyClasse entity.
 */
public interface CompanyClasseSearchRepository extends ElasticsearchRepository<CompanyClasse, Long> {
}
