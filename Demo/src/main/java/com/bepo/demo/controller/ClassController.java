package com.bepo.demo.controller;

import com.bepo.core.anotation.ApiClass;
import com.bepo.core.anotation.ApiMethod;
import com.bepo.core.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
@ApiClass(name = "班级", description = "用于调用班级相关（如查询、新增等）操作的接口")
public class ClassController {

    @GetMapping("/query/class/{id}")
    @ApiMethod(name = "查询班级信息", params = {"Integer id 班级号 1"}, method = "GET", description = "提供 {班级号} 查询班级所有信息")
    public Result queryClass(@PathVariable(value = "id") Integer id){
        HashMap<String, String> clazz = new HashMap<>();
        clazz.put("name", "高等数学1班");
        clazz.put("person_num", "36");
        return new Result<>().ok(clazz);
    }

    @PostMapping("/add/class/")
    @ApiMethod(name = "新增班级", params = {"String name 班级名称 1"}, method = "POST", description = "提供 {班级名} 新增单个班级")
    public Result addClass(String name){
        return new Result<>().ok("新增班级：" + name + "成功");
    }
}
