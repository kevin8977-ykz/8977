package com.bjpowernode.springboot.web;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("/getValue")
    public String getValueString(){
        redisTemplate.boundValueOps("username").set("蔡徐坤");

        String username = (String) redisTemplate.opsForValue().get("username");

        return username;
    }

    @RequestMapping("/getList")
    public List getList(){
        //List添加操作分为：左压栈(后进先出)、右压栈(后进后出，先进先出)
        redisTemplate.boundListOps("class").leftPushAll("林峰","周柏豪","陈奕迅");
        redisTemplate.boundListOps("class").leftPush("周柏豪","陈奕迅");
        redisTemplate.boundListOps("class").leftPush("陈奕迅");

        List aClass = redisTemplate.opsForList().range("class", 0, -1);

        return aClass;
    }


    @RequestMapping("/getSet")
    public Object getSet(){

        redisTemplate.boundSetOps("周杰伦").add("{age:17}");
        redisTemplate.boundSetOps("周杰伦").add("{phone:145621237}");
        redisTemplate.boundSetOps("周杰伦").add("{idCard:789789798789789797},contact:[{xxx:xxxx}]");

        Set aClass = redisTemplate.opsForSet().members("周杰伦");

        return aClass;
    }

    @RequestMapping("/getZSet")
    public Object getZSet(){
        redisTemplate.boundZSetOps("investTop").add("zhangsan",1000);
        redisTemplate.boundZSetOps("investTop").add("lisi",6666);
        redisTemplate.boundZSetOps("investTop").add("wangwu",7777);
        redisTemplate.boundZSetOps("investTop").add("zhoujielun",88888888);


        Set<ZSetOperations.TypedTuple<String>> set= redisTemplate.opsForZSet().reverseRangeByScoreWithScores("investTop", 0, 888888888);

        List<Map<String,Object>> list = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> typedTuple : set) {
            Map<String,Object> map = new HashMap<>();
            Double score = typedTuple.getScore();

            String value = typedTuple.getValue();
            map.put("score",score);
            map.put("value",value);

            list.add(map);
        }

        return list;
    }

    @RequestMapping("/getHash")
    public Object getHash(){
        redisTemplate.boundHashOps("user").put("ls","lisi");
        redisTemplate.boundHashOps("user").put("ww","wagnwu");
        redisTemplate.boundHashOps("user").put("lb","libai");
        redisTemplate.boundHashOps("user").put("jk","jack");

        List<String> conn = new ArrayList<>();
        conn.add("ls");
        conn.add("ww");
        conn.add("lb");
        conn.add("jk");
        List list = redisTemplate.opsForHash().multiGet("user", conn);

        return list;
    }



}
