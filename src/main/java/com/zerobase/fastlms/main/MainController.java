package com.zerobase.fastlms.main;

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


// MainPage 클래스를 만든 목적은 매핑 하기 위해서
// 논리적인 인터넷 주소와 물리적인 파일(프로그래밍을 해야 하므로)을 매핑
// 하나의 주소에 대해서 메소드가 매핑 해준다

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index() {
        String email = "busyheyazoo1007@gmail.com";
        String subject = "안녕하세요. 제로베이스 입니다.";
        String text = "<p>안녕하세요</p><p>반갑습니다</p>";
        mailComponents.sendMail(email, subject, text);

        return "index";
    }

    // request -> WEB -> SERVER
    // response -> SERVER -> WEB
    @RequestMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter printWriter = response.getWriter();

        String message = "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "</head>" +
                "<body>" +
                "<p>hello</p> " +
                "<p>this is fastlms website</p>" +
                "<p> 한글 입니다. </p>" +
                "</body>" +
                "</html>";

        printWriter.write(message);
        printWriter.close();
    }
}
