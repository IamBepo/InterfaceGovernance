package com.bepo.demo.controller;

import com.bepo.core.anotation.ApiClass;
import com.bepo.core.anotation.ApiMethod;
import com.bepo.core.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
@ApiClass(name = "用户", description = "用于调用用户相关（如查询、新增等）操作的接口")
public class UserController {

    @GetMapping("/query/user/{username}")
    @ApiMethod(name = "查询用户信息", params = {"String username 用户名 1"}, method = "GET", description = "提供 {用户名} 查询用户所有信息")
    public Result queryUser(@PathVariable(value = "username") String username){
        HashMap<String, String> user = new HashMap<>();
        user.put("username", "bepo");
        user.put("password", "123456");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Result<>().ok(user);
    }

    @PostMapping("/add/user/")
    @ApiMethod(name = "新增用户", params = {"String username 用户名 1", "String password 密码 1"}, method = "POST", description = "提供 {用户名, 密码} 新增单个用户")
    public Result addUser(String username, String password){
        return new Result<>().ok("新增用户：" + username + "成功");
    }
}
