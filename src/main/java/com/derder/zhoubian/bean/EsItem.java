package com.derder.zhoubian.bean;

import java.util.Date;
import java.util.List;

/**
 * author: zhaolei
 * date: 2014-07-28
 */
public class EsItem {
    long id;
    String title;
    String desc;
    List<Attach> attachList;
    float nowPrice;//现价
    float oldPrice;//原价
    GoodsType type;
    RecencyInfo recencyInfo;//新旧程度
    int changeFlag;//是否支持物物交换
    User fbUser;//发布人
    Date fbDate;//发布时间
    int esType;//转让or求购

    public EsItem(long id, String title, String desc, List<Attach> attachList, float nowPrice, float oldPrice, GoodsType type, RecencyInfo recencyInfo, int changeFlag, User fbUser, Date fbDate, int esType) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.attachList = attachList;
        this.nowPrice = nowPrice;
        this.oldPrice = oldPrice;
        this.type = type;
        this.recencyInfo = recencyInfo;
        this.changeFlag = changeFlag;
        this.fbUser = fbUser;
        this.fbDate = fbDate;
        this.esType = esType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Attach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<Attach> attachList) {
        this.attachList = attachList;
    }

    public float getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(float nowPrice) {
        this.nowPrice = nowPrice;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public RecencyInfo getRecencyInfo() {
        return recencyInfo;
    }

    public void setRecencyInfo(RecencyInfo recencyInfo) {
        this.recencyInfo = recencyInfo;
    }

    public int getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(int changeFlag) {
        this.changeFlag = changeFlag;
    }

    public User getFbUser() {
        return fbUser;
    }

    public void setFbUser(User fbUser) {
        this.fbUser = fbUser;
    }

    public Date getFbDate() {
        return fbDate;
    }

    public void setFbDate(Date fbDate) {
        this.fbDate = fbDate;
    }

    public int getEsType() {
        return esType;
    }

    public void setEsType(int esType) {
        this.esType = esType;
    }
}
