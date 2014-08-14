package com.derder.zhoubian.bean;

/**
 * author: zhaolei
 * date: 2014-07-28
 * 附件
 */
public class Attach {
    long id;
    String name;
    String path;
    String url;
    int size;

    public Attach(long id, String name, String path, String url, int size) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.url = url;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
