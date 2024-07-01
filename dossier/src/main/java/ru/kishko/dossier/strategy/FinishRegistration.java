package ru.kishko.dossier.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinishRegistration implements EmailSendStrategy {

    @Value("${spring.mail.username}")
    private String fromAddress;
    private final JavaMailSender mailSender;

    @Override
    public void sendMail(EmailMessage emailMessage) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(emailMessage.getAddress());
            mailMessage.setSubject(emailMessage.getTheme().name());
            mailMessage.setText("Здравствуйте, завершите оформление");
            mailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("Ошибка отправки письма:", e);
            throw e;
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.FINISH_REGISTRATION;
    }
}