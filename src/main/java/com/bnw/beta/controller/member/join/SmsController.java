package com.bnw.beta.controller.member.join;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SmsController {
    private final DefaultMessageService messageService;
    private static int number;
    public SmsController() {
        this.messageService =  NurigoApp.INSTANCE.initialize("NCSBFVY0KVPY7C71", "2LHF42AJMPEJXKPTT6VJDOVREQK1IQTI", "https://api.coolsms.co.kr");;
    }
    @ResponseBody
    @PostMapping("/sms")
    public Map<String, String> sendSMS(@RequestBody Map<String,String>request){
        String phone = request.get("phone");

        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값


        Message message = new Message();
        message.setFrom("01024185218");
        message.setTo(phone);
        message.setText("[Betago]인증 번호는 " + number + "입니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        Map<String, String> customFields = new HashMap<>();
        customFields.put("number", String.valueOf(number));
        customFields.put("response", String.valueOf(response));

        return customFields;
    }
}