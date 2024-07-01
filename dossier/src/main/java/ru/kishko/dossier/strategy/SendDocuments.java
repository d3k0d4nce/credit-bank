package ru.kishko.dossier.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.kishko.dossier.files.FileService;
import ru.kishko.dossier.utils.FeignControllerClient;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendDocuments implements EmailSendStrategy {

    @Value("${spring.mail.username}")
    private String fromAddress;
    private final FileService fileService;
    private final JavaMailSender mailSender;
    private final FeignControllerClient feignControllerClient;

    @Override
    public void sendMail(EmailMessage emailMessage) {
        log.info("Sending email with documents for statementId: {}", emailMessage.getStatementId());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress);
            helper.setTo(emailMessage.getAddress());
            helper.setSubject(emailMessage.getTheme().name());
            helper.setText("Здравствуйте, отправляем вам пакет документов для оформления кредита.");

            File file = fileService.createCreditFile(String.valueOf(emailMessage.getStatementId()));
            helper.addAttachment("credit-file.txt", file);

            mailSender.send(message);
            log.info("Email sent successfully.");
            feignControllerClient.updateApplicationStatus(String.valueOf(emailMessage.getStatementId()));
            log.info("Application status updated successfully.");
        } catch (MessagingException | IOException e) {
            log.error("Error sending email with documents for statementId: {}", emailMessage.getStatementId(), e);
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.YOUR_LOAN_DOCUMENTS;
    }
}