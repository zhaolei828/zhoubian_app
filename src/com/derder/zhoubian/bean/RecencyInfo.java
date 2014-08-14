package com.derder.zhoubian.bean;

/**
 * author: zhaolei
 * date: 2014-07-28
 * 新旧情况
 */
public class RecencyInfo {
    int id;
    String recency;

    public RecencyInfo() {
    }

    public RecencyInfo(int id) {
        this.id = id;
    }

    public RecencyInfo(int id, String recency) {
        this.id = id;
        this.recency = recency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecency() {
        return recency;
    }

    public void setRecency(String recency) {
        this.recency = recency;
    }
}
