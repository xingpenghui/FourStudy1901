package com.qfedu.fourstudy.transport;

import com.alibaba.fastjson.JSON;
import com.qfedu.fourstudy.transport.model.Project;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *@Author feri
 *@Date Created in 2019/6/20 14:47
 */
public class Es_Main1 {
    public static void main(String[] args) throws UnknownHostException {
        //1、创建连接配置信息 指定集群名称
        Settings settings=Settings.builder().put("cluster.name","qfjava").build();
        //2、创建连接对象
        TransportClient client=new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("39.105.189.141"),9300));
        //3、操作ES  CRUD  新增、查询（Retrieve）、修改、删除
        //新增
//        Project project=new Project();
//        project.setId(1);
//        project.setName("人人都是千万富翁");
//        project.setType("财富");
//
//        IndexResponse response=client.prepareIndex("j1901","project",project.getId()+"").
//                setSource(JSON.toJSONString(project),XContentType.JSON).get();
//        System.out.println("新增："+response.getResult().name());
//        for(int i=2;i<102;i++){
//            Project p=new Project();
//            p.setId(i);
//            p.setName("人人都是"+i+"万富翁");
//            p.setType("财富");
//
//            IndexResponse r=client.prepareIndex("j1901","project",p.getId()+"").
//                    setSource(JSON.toJSONString(p),XContentType.JSON).get();
//            System.out.println("新增："+r.getResult().name());
//        }
//        Project project=new Project();
//        project.setId(1);
//        project.setName("人人都会Java");
//        project.setType("编程语言");
//        //id不存在就是新增 存在就是修改
//        IndexResponse response=client.prepareIndex("j1901","project",project.getId()+"").
//                setSource(JSON.toJSONString(project),XContentType.JSON).get();
//        System.out.println("新增："+response.getResult().name());
        //查询
        GetResponse getResponse=client.prepareGet("j1901","project","11").get();
        String json=getResponse.getSourceAsString();
        System.out.println("查询："+json);

        //修改 参数说明1、待解析的json 2结果类型的class对象
        Project updatePro=JSON.parseObject(json,Project.class);
        updatePro.setName("人人都需要休息，下课吧");
        UpdateResponse updateResponse=client.prepareUpdate("j1901","project","1").
                setDoc(JSON.toJSONString(updatePro),XContentType.JSON).get();
        System.out.println("修改："+updateResponse.status().toString());
        //删除
        DeleteResponse deleteResponse= client.prepareDelete("j1901","project","1").get();
        System.out.println("删除"+deleteResponse.status().toString());
        //4、关闭
        client.close();
    }
}
