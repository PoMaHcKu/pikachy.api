package by.itra.pikachy.api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;
    @Value("${mail.subject}")
    private String mailSubject;

    public void sendSimpleMessage(String recipient, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(mailSubject);
        message.setText(text);
        mailSender.send(message);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
