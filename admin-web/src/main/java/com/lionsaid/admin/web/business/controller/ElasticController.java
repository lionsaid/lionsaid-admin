package com.lionsaid.admin.web.business.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.lionsaid.admin.web.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/elastic")
public class ElasticController {
    private final ElasticsearchClient elasticsearchClient;


    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity search(@PathVariable Long id) throws IOException {
        String term = "hospital_name";
        String term1 = "hospital_type";
        String term2 = "hospital_level";
        String term3 = "id";

           SearchResponse search = elasticsearchClient.search(builder -> {
            builder.index("provider_hospital_info_demo")
                    .trackTotalHits(t -> t.enabled(true))
                    .aggregations(term, Aggregation.of(agg -> agg.terms(t -> t.field(term + ".keyword").size(3000))
                            .aggregations(term1, Aggregation.of(agg1 -> agg1.terms(t -> t.field(term1 + ".keyword").size(1))))
                            .aggregations(term2, Aggregation.of(agg1 -> agg1.terms(t -> t.field(term2 + ".keyword").size(1))))
                    ))
            ;
            return builder;
        }, Void.class);
        Map<String, Aggregate> aggregate = search.aggregations();
       // aggregate._kind().jsonValue()
        List<StringTermsBucket> buckets = aggregate
                .get(term)
                .sterms()
                .buckets().array();

        for (StringTermsBucket bucket: buckets) {
            log.error("There are " + bucket.docCount() +
                    " bikes under " + bucket.key().stringValue());
        }
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success( aggregate));
    }


}