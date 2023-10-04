package com.bnw.beta.controller.member.join;

import com.bnw.beta.service.member.MailSendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailSendServiceImpl mailSendService;

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(@RequestBody Map<String, String> request) {
        String mail = request.get("mail");
        int number = mailSendService.sendMail(mail);
        return String.valueOf(number);
    }
}