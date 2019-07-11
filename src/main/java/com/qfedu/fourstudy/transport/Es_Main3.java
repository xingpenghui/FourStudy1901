package com.qfedu.fourstudy.transport;

import com.alibaba.fastjson.JSON;
import com.qfedu.fourstudy.transport.model.Project;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 *@Author feri
 *@Date Created in 2019/6/20 15:46
 */
public class Es_Main3 {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings=Settings.builder().put("cluster.name","qfjava").build();
        //2、创建连接对象
        TransportClient client=new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("39.105.189.141"),9300));
        //3、各种查询操作
        //创建查询的条件方式 范围查询
        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("id").gt("1600").lt("2000");
        WildcardQueryBuilder wildcardQueryBuilder=QueryBuilders.wildcardQuery("name","*10*");
        //条件查询
        TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("id","1010");
        //TermsQueryBuilder termsQueryBuilder=QueryBuilders.
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.must(rangeQueryBuilder).must(wildcardQueryBuilder);
        //生成查询对象 资源查询对象
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);

       // sourceBuilder.sort("id",SortOrder.DESC);
        //创建ES查询条件对象
        SearchRequestBuilder ssb=client.prepareSearch("java1901").
                setTypes("project").
                setFrom(0).setSize(5).addSort("id",SortOrder.DESC);
        //查询的响应对象
        SearchResponse searchResponse= ssb.setQuery(sourceBuilder.query()).get();
        Iterator<SearchHit> hitIterator=searchResponse.getHits().iterator();
        while (hitIterator.hasNext()){
            System.out.println(hitIterator.next().getSourceAsString());
        }


        //4、关闭
        client.close();;
    }
}
