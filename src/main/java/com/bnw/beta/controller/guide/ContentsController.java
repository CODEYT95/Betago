package com.bnw.beta.controller.guide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contents")
@Controller
public class ContentsController {

    @GetMapping("/contentsGame")
    public String contentsGameIntro(Model model) {
        model.addAttribute("contents",1);
        return "guide/contents/contentsGame";
    }

    @GetMapping("/contentsTutorial")
    public String contentsTutorialIntro(Model model) {
        model.addAttribute("tutorial",1);
        return "guide/contents/contentsTutorial";
    }

    @GetMapping("/contentsVideo")
    public String contentsVideoIntro(Model model) {
        model.addAttribute("video",1);
        return "guide/contents/contentsVideo"; }
}


