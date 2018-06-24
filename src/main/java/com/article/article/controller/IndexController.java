package com.article.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Adrisson
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login/login");
    }

    @GetMapping("/denied")
    public ModelAndView denied() {
        return new ModelAndView("denied");
    }
}
