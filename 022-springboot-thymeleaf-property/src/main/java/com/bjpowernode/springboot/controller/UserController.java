package com.bjpowernode.springboot.controller;

import com.bjpowernode.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.*;

@Controller
@RequestMapping("/each")
public class UserController {

    @RequestMapping("/list")
    public String eachList(Model model) {

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(100 + i);
            user.setName("张" + i);
            user.setPhone("1361234567" + i);
            user.setAddress("北京市大兴区" + i);
            userList.add(user);
        }
        model.addAttribute("userList", userList);


        Set<User> userSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(100 + i);
            user.setName("周" + i);
            user.setPhone("1361777777" + i);
            user.setAddress("上海市静安区" + i);
            userSet.add(user);
        }
        model.addAttribute("userSet", userSet);

        String[] strArr = new String[]{"周杰伦","张国荣","李小龙","陈奕迅","林宥嘉","周柏豪"};
        model.addAttribute("strArr", strArr);


        Map<Integer,Object> userMaps = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("毛爷爷"+i);
            user.setPhone("1597777777"+i);
            user.setAddress("深证市南山区"+i);
            userMaps.put(i,user);
        }

        model.addAttribute("userMaps",userMaps);

        List<Map<Integer,List<User>>> myList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            Map<Integer,List<User>> myMap = new HashMap<>();

            for (int j = 0; j < 2; j++) {
                List<User>  myUserList = new ArrayList<>();

                for (int k = 0; k < 3; k++) {
                    User user = new User();
                    user.setId(k);
                    user.setName("刘备"+k);
                    user.setPhone("1350999999"+k);
                    user.setAddress("广州市天河区"+i);
                    myUserList.add(user);

                }
                myMap.put(j,myUserList);
            }
            myList.add(myMap);

        }
        model.addAttribute("myList",myList);



        return "each";
    }

}