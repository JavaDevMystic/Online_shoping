package uz.pdp.myappfigma.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, LocalDateTime> codeExpiryMap = new HashMap<>();


    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendVerificationEmail(String toEmail) {
        String code = UUID.randomUUID().toString().substring(0, 6);

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(2);
        codeExpiryMap.put(code, expiryTime);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + code + "\nThis code will expire in 2 minutes.");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }

        return code;
    }

    public boolean isCodeValid(String code) {
        if (codeExpiryMap.containsKey(code)) {
            LocalDateTime expiryTime = codeExpiryMap.get(code);
            if (LocalDateTime.now().isBefore(expiryTime)) {
                return true;
            } else {
                codeExpiryMap.remove(code);
            }
        }
        return false;
    }
}

