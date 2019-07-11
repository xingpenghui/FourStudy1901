package com.qfedu.fourstudy.webmagic;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.cj.jdbc.Driver;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 11:17
 * 处理爬取的博客园的文章列表数据  存储到Mysql
 */
public class BkyPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println(resultItems.getAll());
        List<BkyEnity> enityList= resultItems.get("data");
        DruidDataSource druidDataSource=new DruidDataSource();

        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_1901?serverTimezone=UTC&characterEncoding=UTF8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

        try {
            PreparedStatement preparedStatement=druidDataSource.
                    getConnection().prepareStatement("insert into t_bky(title,summary ,author,pubtime,detailurl,createtime) values(?,?,?,?,?,now())");
            for(BkyEnity be:enityList){
                preparedStatement.setString(1,be.getTitle());
                preparedStatement.setString(2,be.getSummary());
                preparedStatement.setString(3,be.getAuthor());
                preparedStatement.setString(4,be.getPubTime());
                preparedStatement.setString(5,be.getDetailUrl());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
