package com.bepo.core.utils;


import com.bepo.core.constants.CodeConstants;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result ok(T t){
        this.setCode(CodeConstants.CODE_SUS);
        this.setMsg("请求成功");
        this.setData(t);
        return this;
    }

    public Result fail(String msg){
        this.setCode(CodeConstants.CODE_FAIL);
        this.setMsg(msg);
        return this;
    }


    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}