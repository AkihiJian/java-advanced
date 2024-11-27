package com.icycoke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: JianJian
 * @date: 2023-11-21 10:40
 * @description:
 */

@Controller
public class DemoController {

    @RequestMapping("/demo")
    @ResponseBody
    public String demo(){
         return "demo";
    }
}
