package com.bnw.beta.controller.guide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/introduction")
@Controller
public class IntroductionController {
    @GetMapping("/site")
    public String siteIntroduction() {return "guide/introduction/siteIntroduction";}
}
