package com.itravel.app.repository.search;

import com.itravel.app.domain.CompanyAgency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyAgency entity.
 */
public interface CompanyAgencySearchRepository extends ElasticsearchRepository<CompanyAgency, Long> {
}
