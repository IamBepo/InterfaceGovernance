package com.bepo.demo.controller;

import com.bepo.core.anotation.ApiClass;
import com.bepo.core.anotation.ApiMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/test")
@ApiClass(name = "测试", description = "测试接口集合")
public class DemoController {

    @GetMapping("/{id}")
    @ResponseBody
    @ApiMethod(name = "查询id", params = {"Integer id"}, method = "GET", description = "查询指定id信息")
    public String query(@PathVariable(value = "id") Integer id){
        return "qureyId：" + id;
    }

    @PostMapping
    @ResponseBody
    @ApiMethod(name = "新增name", params = {"String name"}, method = "POST", description = "新增信息")
    public String add(@RequestBody String name){
        return "add：" + name;
    }
}
