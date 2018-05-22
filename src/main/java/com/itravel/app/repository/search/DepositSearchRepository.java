package com.itravel.app.repository.search;

import com.itravel.app.domain.Deposit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Deposit entity.
 */
public interface DepositSearchRepository extends ElasticsearchRepository<Deposit, Long> {
}
