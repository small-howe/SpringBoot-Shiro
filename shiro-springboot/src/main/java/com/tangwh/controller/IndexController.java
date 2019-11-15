package com.tangwh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/","index"})
    public String toIndex(Model model){
      model.addAttribute("msg", "Hello Shiro");
        return "index";
    }
}
