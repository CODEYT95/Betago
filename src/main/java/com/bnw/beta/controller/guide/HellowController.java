package com.bnw.beta.controller.guide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HellowController {

    @GetMapping("/hellow")
    public String introduceBaduk(Model model) {
      return "/guide/hellow/greetings";
    }

}
