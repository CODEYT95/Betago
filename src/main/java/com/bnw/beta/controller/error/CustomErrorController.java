package com.bnw.beta.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        // 사용자 정의 오류 페이지로 리다이렉션
        return "error"; // error.html 또는 오류 페이지의 이름
    }

}
