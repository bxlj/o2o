package com.lj.o2o;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        boolean a = true;
        boolean b = true;
        if (a == b) {
            System.out.println("we");
        }
        int i1 = 1;

        return "hello";
    }
}