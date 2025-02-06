package com.bepo.core.action;

import com.bepo.core.runner.InterfaceGovernanceRunner;
import com.bepo.core.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ig")
public class IgController {

    @GetMapping("/get/info")
    public Result getClassInfo(){
        return new Result<>().ok(InterfaceGovernanceRunner.apiInfo);
    }
}
