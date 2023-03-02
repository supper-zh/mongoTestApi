package com.example.mongotestapi.controller;
import com.example.mongotestapi.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: 59157
 * Date: 2020/12/2
 * Time: 14:51
 */
@Controller
public class JokeController {

    @Autowired
    JokeService jokeService;

    /**
     * 京东万象-笑话api
     */
    @RequestMapping("/getJoke")
    @ResponseBody
    public String jokeApi() throws Exception {
        //return "getjoke";
        jokeService.httpRequest();
        return "调用的数据已保存！";
    }
}