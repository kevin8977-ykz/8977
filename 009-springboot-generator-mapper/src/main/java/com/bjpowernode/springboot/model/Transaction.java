package com.bjpowernode.springboot.model;

public class Transaction {
    private String id;

    private String owner;

    private Long amountofmoney;

    private String name;

    private String expectedclosingdate;

    private String customerid;

    private String stage;

    private String type;

    private String source;

    private String activityid;

    private String contactsid;

    private String description;

    private String createby;

    private String createtime;

    private String editby;

    private String edittime;

    private String contactsummary;

    private String nextcontacttime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAmountofmoney() {
        return amountofmoney;
    }

    public void setAmountofmoney(Long amountofmoney) {
        this.amountofmoney = amountofmoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpectedclosingdate() {
        return expectedclosingdate;
    }

    public void setExpectedclosingdate(String expectedclosingdate) {
        this.expectedclosingdate = expectedclosingdate;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getContactsid() {
        return contactsid;
    }

    public void setContactsid(String contactsid) {
        this.contactsid = contactsid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEditby() {
        return editby;
    }

    public void setEditby(String editby) {
        this.editby = editby;
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getContactsummary() {
        return contactsummary;
    }

    public void setContactsummary(String contactsummary) {
        this.contactsummary = contactsummary;
    }

    public String getNextcontacttime() {
        return nextcontacttime;
    }

    public void setNextcontacttime(String nextcontacttime) {
        this.nextcontacttime = nextcontacttime;
    }
}