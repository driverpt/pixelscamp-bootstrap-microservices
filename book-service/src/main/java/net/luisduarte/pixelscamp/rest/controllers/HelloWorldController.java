package net.luisduarte.pixelscamp.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //@Autowired
    //private Environment environment;

    @RequestMapping("/")
    public String foo(){
        return "foo";
    }
}
