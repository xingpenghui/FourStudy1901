package com.qfedu.fourstudy.webmagic;

import lombok.Data;

/**
 *@Author feri
 *@Date Created in 2019/6/17 11:02
 */
@Data
public class BkyEnity {
    private int id;
    private String title;
    private String summary;
    private String detailUrl;
    private String author;
    private String pubTime;

    public BkyEnity() {
    }

    public BkyEnity(int id, String title, String summary, String detailUrl, String author, String pubTime) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.detailUrl = detailUrl;
        this.author = author;
        this.pubTime = pubTime;
    }
}
