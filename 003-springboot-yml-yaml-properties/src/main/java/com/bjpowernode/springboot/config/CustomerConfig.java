package com.bjpowernode.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "sz2005")
public class CustomerConfig {

    private String uname;
    private String hobby;
    private Integer age;

    private List list;

    private Map map;

    private String[] array;

    private Set<String> set;


    @Override
    public String toString() {
        return "CustomerConfig{" +
                "uname='" + uname + '\'' +
                ", hobby='" + hobby + '\'' +
                ", age=" + age +
                ", list=" + list +
                ", map=" + map +
                ", array=" + Arrays.toString(array) +
                ", set=" + set +
                '}';
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
