package com.bnw.beta.controller.guide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manual")
@Controller
public class ManualController {
    @GetMapping("/list")
    public String manualList(Model model) {
        model.addAttribute("manualList",1);
        return "guide/manual/manualList";}
}
