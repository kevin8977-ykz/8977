package com.bjpowernode.p2p.model.user;

import java.io.Serializable;

public class FinanceAccount implements Serializable {
    private Integer id;

    private Integer uid;

    private Double availableMoney;

    @Override
    public String toString() {
        return "FinanceAccount{" +
                "id=" + id +
                ", uid=" + uid +
                ", availableMoney=" + availableMoney +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Double availableMoney) {
        this.availableMoney = availableMoney;
    }
}