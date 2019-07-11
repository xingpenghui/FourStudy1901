package com.qfedu.fourstudy.transport;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/20 15:34
 * 基于Transport实现对象ES的封装处理
 */
public class EsUtil {
    private TransportClient client;
    public EsUtil(String clusterName,String host,int port){
        Settings settings=Settings.builder().put("cluster.name",clusterName).build();
        //2、创建连接对象
        try {
            client=new PreBuiltTransportClient(settings).addTransportAddress(
                    new InetSocketTransportAddress(InetAddress.getByName(host),port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    //新增
    public boolean save(String indexName,String typeName,String id,String res){
        IndexResponse response=client.prepareIndex(indexName,typeName,id).setSource(res,XContentType.JSON).get();
        return response.getResult().name().equals("CREATED");
    }
    //修改
    public boolean update(String indexName,String typeName,String id,String res){
        UpdateResponse updateResponse=client.prepareUpdate(indexName,typeName,id).setDoc(res,XContentType.JSON).get();
        return updateResponse.status().name().equals("OK");
    }
    //删除
    public boolean delete(String indexName,String typeName,String id){
        DeleteResponse deleteResponse=client.prepareDelete(indexName, typeName, id).get();
        return deleteResponse.status().name().equals("OK");
    }
    //查询
    public String getById(String indexName,String typeName,String id){
        GetResponse getResponse=client.prepareGet(indexName, typeName, id).get();
        return getResponse.getSourceAsString();
    }
    //自定义泛型
    public <T> T getByIdObj(String indexName,String typeName,String id,Class<T> clz){
        String json=getById(indexName, typeName, id);
        if(json!=null){
            return JSON.parseObject(json,clz);
        }else {
            return null;
        }
    }
    //批量新增
    public <T> boolean batchSave(String indexName,String typeName,List<String> ids,List<String> res){
        BulkRequestBuilder requestBuilder=client.prepareBulk();
        for(int i=0;i<ids.size();i++){
            requestBuilder.add(client.prepareIndex(indexName,typeName,ids.get(i)).
                    setSource(res.get(i),XContentType.JSON));
        }
        return requestBuilder.get().status().name().equals("OK");
    }
    //批量修改
    public <T> boolean batchUpdate(String indexName,String typeName,List<String> ids,List<String> res){
        BulkRequestBuilder requestBuilder=client.prepareBulk();
        for(int i=0;i<ids.size();i++){
            requestBuilder.add(client.prepareUpdate(indexName,typeName,ids.get(i)).
                    setDoc(res.get(i),XContentType.JSON));
        }
        return requestBuilder.get().status().name().equals("OK");
    }
    //批量删除
    public <T> boolean batchDelete(String indexName,String typeName,List<String> ids){
        BulkRequestBuilder requestBuilder=client.prepareBulk();
        for(int i=0;i<ids.size();i++){
            requestBuilder.add(client.prepareDelete(indexName,typeName,ids.get(i)));
        }
        return requestBuilder.get().status().name().equals("OK");
    }
    //复杂查询-单值
    public String searhValue(String indexName, String typeName,String field,Object value){
        TermQueryBuilder termQueryBuilder= QueryBuilders.termQuery(field,value);
        List<String> list= search(indexName,typeName,0,1,termQueryBuilder);
        if(list!=null) {
            return list.get(0);
        }else {
            return null;
        }
    }
    //复杂查询-多值
    //复杂查询-模糊
    //复杂查询-范围

    //查询
    public List<String> search(String indexName, String typeName,int start,int count, QueryBuilder queryBuilder){
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        SearchResponse searchResponse=client.prepareSearch(indexName).setTypes(typeName).setFrom(start).setSize(count).setQuery(sourceBuilder.query()).get();
        SearchHit[] hits=searchResponse.getHits().getHits();
        List<String> list=new ArrayList<>();
        for(SearchHit sh:hits){
            list.add(sh.getSourceAsString());
        }
        return list;
    }
    public <T> List<T> searchList(String indexName, String typeName,int start,int count, QueryBuilder queryBuilder,Class<T> clz){
        List<String> arr=search(indexName, typeName, start, count, queryBuilder);
        String json=JSON.toJSONString(arr);
        List<T> list=JSON.parseArray(json,clz);
        return list;
    }


}
