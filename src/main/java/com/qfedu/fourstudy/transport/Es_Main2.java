package com.qfedu.fourstudy.transport;

import com.alibaba.fastjson.JSON;
import com.qfedu.fourstudy.transport.model.Project;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *@Author feri
 *@Date Created in 2019/6/20 15:46
 */
public class Es_Main2 {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings=Settings.builder().put("cluster.name","qfjava").build();
        //2、创建连接对象
        TransportClient client=new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("39.105.189.141"),9300));
        //3、批处理
        BulkRequestBuilder request=client.prepareBulk();
        //新增10000条数据
        for(int i=1;i<10001;i++){
            Project project=new Project();
            project.setId(i);
            project.setName("高端程序猿："+i);
            project.setType("攻城狮");
            request.add(client.prepareIndex("java1901","project",i+"").
                    setSource(JSON.toJSONString(project),XContentType.JSON));
//            request.add(client.prepareDelete("java1901","project",i+""));
//            request.add(client.prepareUpdate("java1901","project",i+"").setDoc(JSON.toJSONString(project),XContentType.JSON));
        }
        //request.execute();
        System.out.println("批处理:"+request.get().status().toString());
        //4、关闭
        client.close();;
    }
}
