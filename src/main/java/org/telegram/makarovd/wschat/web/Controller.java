package org.telegram.makarovd.wschat.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping("/message")
    public String getMessage(){
        return "Some text\n";
    }
}
