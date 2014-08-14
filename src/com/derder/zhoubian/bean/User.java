package com.derder.zhoubian.bean;

/**
 * author: zhaolei
 * date: 2014-07-28
 */
public class User {
    long id;
    String telno;
    String nickName;
    String password;
    JuzhuDi jzd;
    int rzFlag;//是否认证
    String rzInfo;//认证信息
    int sex;
    String portrait;//头像
    String aiHao;//爱好

    public User(JuzhuDi jzd) {
        this.jzd = jzd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JuzhuDi getJzd() {
        return jzd;
    }

    public void setJzd(JuzhuDi jzd) {
        this.jzd = jzd;
    }

    public int getRzFlag() {
        return rzFlag;
    }

    public void setRzFlag(int rzFlag) {
        this.rzFlag = rzFlag;
    }

    public String getRzInfo() {
        return rzInfo;
    }

    public void setRzInfo(String rzInfo) {
        this.rzInfo = rzInfo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAiHao() {
        return aiHao;
    }

    public void setAiHao(String aiHao) {
        this.aiHao = aiHao;
    }
}
