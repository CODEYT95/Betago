package com.bnw.beta.service.member;

import com.bnw.beta.config.vaildation.member.PasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendServiceImpl{

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private static final String senderEmail= "uu940903@gmail.com";
    private static final String senderEmail2= "elekdrnddl@gmail.com";
    private static int number;

    //인증번호 생성
    public static int createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
        System.out.println("난수는 찍히나요?"+number);
        return number;
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail){
        System.out.println("메일주소를 받고 있나요?" + mail );
        MimeMessage message = CreateMail(mail.trim());
        javaMailSender.send(message);

        return number;
    }
    
    /////임시비밀번호 발송////김현민
    public void sendTemporaryPassword(String email, String tempPassword) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            // 메시지 내용 채우기
            message.setFrom(senderEmail2);
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email)); // '받는 사람' 이메일 설정
            message.setSubject("임시 비밀번호 발송 안내"); // 이메일 제목 설정

            // 비밀번호 재설정 URL. 실제 서비스에서는 보안을 위한 토큰이나 유니크한 식별자를 포함해야 합니다.
            String resetUrl = "http://localhost:8800/resetPw?email=" + email;

        /* 기존꺼
           message.setText("귀하의 임시 비밀번호는 다음과 같습니다: " + tempPassword, "utf-8", "html"); // 이메일 본문 설정
        */
            // HTML 형식의 이메일 내용 생성
            String emailContent = "<p>귀하의 임시 비밀번호는 다음과 같습니다: " + tempPassword + "</p>"
                    + "<p>비밀번호를 재설정하려면 다음 링크를 클릭하세요: "
                    + "<a href=\"" + resetUrl + "\">비밀번호 재설정</a></p>";

            message.setContent(emailContent, "text/html;charset=UTF-8"); // 이메일 본문 설정

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("임시 비번 전송 실패", e);
        }


    }
}