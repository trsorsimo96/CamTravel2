package com.itravel.app.repository.search;

import com.itravel.app.domain.ModePayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ModePayment entity.
 */
public interface ModePaymentSearchRepository extends ElasticsearchRepository<ModePayment, Long> {
}
