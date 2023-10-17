package com.bnw.beta.controller.guide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contents")
@Controller
public class ContentsController {

    @GetMapping("/contentsGame")
    public String contentsIntro() {
        return "guide/contents/contentsGame";
    }
}
