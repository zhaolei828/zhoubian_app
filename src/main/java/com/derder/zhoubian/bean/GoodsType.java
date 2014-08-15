package com.derder.zhoubian.bean;

/**
 * author: zhaolei
 * date: 2014-07-28
 */
public class GoodsType {
    private int id;
    private String typeName;
    private String icon;

    public GoodsType() {
    }

    public GoodsType(int id) {
        this.id = id;
    }

    public GoodsType(int id, String typeName, String icon) {
        this.id = id;
        this.typeName = typeName;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
