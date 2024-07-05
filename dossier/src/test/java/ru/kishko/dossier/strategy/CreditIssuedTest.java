package ru.kishko.dossier.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreditIssuedTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private CreditIssued creditIssued;

    @Test
    void sendMail() {
        EmailMessage emailMessage = new EmailMessage("test@example.com", Theme.CREDIT_ISSUED, UUID.randomUUID());
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo("test@example.com");
        expectedMailMessage.setSubject(Theme.CREDIT_ISSUED.name());
        expectedMailMessage.setText("Здравствуйте, документы подписаны, кредит одобрен!");

        creditIssued.sendMail(emailMessage);

        verify(mailSender, times(1)).send(expectedMailMessage);
    }
}