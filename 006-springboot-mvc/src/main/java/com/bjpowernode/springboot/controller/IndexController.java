package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.*;

/**
 * SpringMVC的新注解：
 *      @RestController = @ResponseBody + @Controller
 *          将被修饰的类放入到spring容器中，并返回json数据
 *
 *      @RestControllerAdvice = @ResponseBody + @ControllerAdvice
 *
 *      @GetMapping:查询
 *      @PostMapping：新增
 *      @PutMapping:修改
 *      @DeleteMapping:删除
 */
@RestController
public class IndexController {

    @RequestMapping("/get1")
    public String getMessage1(){
        return "getMessage1 execute ...";
    }

    @RequestMapping(value = "/get2",method = RequestMethod.POST)
    public String getMessage2(){
        return "getMessage2 execute ...";
    }

    @GetMapping("/get3")
    public String getMessage3(){
        return "get  mapping  execute ...";
    }
    @PostMapping("/get4")
    public String getMessage4(){
        return "Post  mapping  execute ...";
    }
    @PutMapping("/get5")
    public String getMessage5(){
        return "Put  mapping  execute ...";
    }
    @DeleteMapping("/get6")
    public String getMessage6(){
        return "Delete  mapping  execute ...";
    }



    @GetMapping("/{id}")
    public String mess(@PathVariable String id){
        return " restFul api execute..." + id;
    }




}
