package com.derder.zhoubian.bean;

/**
 * author: zhaolei
 * date: 2014-07-28
 * 居住地
 */
public class JuzhuDi {
    int id;
    String diName;//地名

    public JuzhuDi(int id, String diName) {
        this.id = id;
        this.diName = diName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiName() {
        return diName;
    }

    public void setDiName(String diName) {
        this.diName = diName;
    }
}
