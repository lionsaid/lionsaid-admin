package com.lionsaid.admin.web.datasync;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.model.po.DataSyncJob;
import com.lionsaid.admin.web.model.po.DataSyncJobFilter;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ElasticsearchDataSyncFunction extends DataSyncFunction {
    private Map<String, List<DataSyncJobFilter>> filter;
    private DataSyncDataSource source;
    private DataSyncJob dataSyncJob;
    private JSONArray failInfo;
    private ElasticsearchClient elasticsearchClient;

    public ElasticsearchDataSyncFunction(Map<String, List<DataSyncJobFilter>> filter, DataSyncDataSource source, DataSyncJob dataSyncJob, JSONArray failInfo) {
        this.filter = filter;
        this.source = source;
        this.dataSyncJob = dataSyncJob;
        this.failInfo = failInfo;
        this.elasticsearchClient = getElasticsearchClient(this.source);
    }

    private ElasticsearchClient getElasticsearchClient(DataSyncDataSource target) {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elasticsearchProperties.getXpackUsername()", "elasticsearchProperties.getXpackPassword()"));
        HttpHost httpHost = HttpHost.create("host");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder
                        /**
                         * 加载用户名密码
                         */
                        .setDefaultCredentialsProvider(credentialsProvider)
        );
        ElasticsearchTransport transport = new RestClientTransport(
                restClientBuilder.build(), new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    @SneakyThrows
    @Override
    public DataSyncResult queryForStream(DataSyncResult dataSyncResult) {
        if (StringUtils.isNotEmpty(dataSyncResult.getScrollId())) {
            ScrollResponse<JSONObject> scrollResponse = elasticsearchClient.scroll(ScrollRequest.of(o -> o.scrollId(dataSyncResult.getScrollId())), JSONObject.class);
            return DataSyncResult.builder().result(scrollResponse.hits().hits().stream().map(Hit::source).toList().stream()).scrollId(scrollResponse.scrollId()).build();
        }
        // 构造搜索请求
        SearchRequest searchRequest = SearchRequest.of(o -> o.index(Arrays.stream(dataSyncJob.getTargetTable().split(",")).toList()).scroll(sc -> sc.offset(5)));
        SearchResponse<JSONObject> searchResponse = elasticsearchClient.search(searchRequest, JSONObject.class);
        return DataSyncResult.builder().result(searchResponse.hits().hits().stream().map(Hit::source).toList().stream()).scrollId(searchResponse.scrollId()).build();
    }

    @SneakyThrows
    @Override
    public Boolean exist(JSONObject result) {
        List<Query> list = Lists.newArrayList();
        Arrays.stream(dataSyncJob.getTargetId().split(",")).forEach(o -> list.add(Query.of(b -> b.match(mq -> mq.field(o).query(result.getString(o))))));
        CountResponse countResponse =
                elasticsearchClient.count(CountRequest.of(o -> o.index(Arrays.stream(dataSyncJob.getTargetTable().split(",")).toList()).query(q -> q.bool(b -> b.must(list)))));
        return countResponse.count() > 0;
    }

    @SneakyThrows
    @Override
    public int update(JSONObject result) {


       // elasticsearchClient.update(UpdateRequest.of(o -> o.id("").id("").doc("")));
        // 执行更新操作...
        return 0;
    }

    @Override
    public int insert(JSONObject result) {
      return 0;
    }



    private JSONObject mapFilter(JSONObject jsonObject, Map<String, List<DataSyncJobFilter>> filter) {
        filter.forEach((key, filterList) -> {
            if (jsonObject.containsKey(key)) {
                filterList.forEach(o -> {
                    JSONObject setting = new JSONObject();
                    if (StringUtils.isNotEmpty(o.getSetting())) {
                        setting = JSONObject.parseObject(o.getSetting());
                    }
                    switch (o.getFilterType()) {
                        case "json_encode":
                            jsonObject.put(key, JSON.parse(jsonObject.getString(key)).toString());
                            break;
                        case "url_decode":
                            jsonObject.put(key, URLDecoder.decode(jsonObject.getString(key), setting.containsKey("charset") ? Charset.forName(setting.getString("charset")) : Charset.forName("utf-8")));
                            break;
                        case "url_encode":
                            jsonObject.put(key, URLEncoder.encode(jsonObject.getString(key), setting.containsKey("charset") ? Charset.forName(setting.getString("charset")) : Charset.forName("utf-8")));
                            break;
                        case "replace_all":
                            jsonObject.put(key, jsonObject.getString(key).replaceAll(setting.getString("regex"), setting.getString("replacement")));
                            break;
                        case "trim":
                            jsonObject.put(key, jsonObject.getString(key).trim());
                            break;
                        default:
                            break;

                    }
                });
            }
        });
        return jsonObject;
    }
}
