package com.ironyard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by matthewhug on 3/21/17.
 */
@Controller
public class RouteController {

    @RequestMapping(path = "/j/list")
    public String goToJobList(){
        return "/jobs/list.html";
    }

    @RequestMapping(path = "/")
    public String goHome(){
        System.out.println("You called goHome()");
        return "/index.html";
    }
}
