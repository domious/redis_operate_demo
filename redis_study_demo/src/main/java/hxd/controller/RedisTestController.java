package hxd.controller;

import hxd.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")

public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/test")
    @ResponseBody
    public String test(){
        try {
            redisUtil.set("aaa","bbb",1);
            String value = redisUtil.get("aaa",1).toString();
            return value;
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
}
