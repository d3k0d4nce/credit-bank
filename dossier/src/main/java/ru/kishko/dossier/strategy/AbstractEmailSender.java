package ru.kishko.dossier.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.EmailMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractEmailSender implements EmailSendStrategy {

    @Value("${spring.mail.username}")
    protected String fromAddress;
    protected final JavaMailSender mailSender;

    @Override
    public void sendMail(EmailMessage emailMessage) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(emailMessage.getAddress());
            mailMessage.setSubject(emailMessage.getTheme().name());
            mailMessage.setText(getEmailText(emailMessage));

            mailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("Ошибка отправки письма:", e);
            throw e;
        }
    }

    protected abstract String getEmailText(EmailMessage emailMessage);
}
