package ktpm.projectsoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    JavaMailSender sender;

    public void sendEmail(String token, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bui269952@gmail.com");
        message.setTo(email);
        message.setSubject("Xac nhan dang ky");
        message.setText(token);
        sender.send(message);
    }

}