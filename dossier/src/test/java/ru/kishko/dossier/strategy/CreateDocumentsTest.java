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
class CreateDocumentsTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private CreateDocuments createDocuments;

    @Test
    void sendMail() {
        EmailMessage emailMessage = new EmailMessage("test@example.com", Theme.CREATE_DOCUMENT, UUID.randomUUID());
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo("test@example.com");
        expectedMailMessage.setSubject(Theme.CREATE_DOCUMENT.name());
        expectedMailMessage.setText("Здравствуйте, ваша заявка одобрена, ожидайте отправки документов для подписания");

        createDocuments.sendMail(emailMessage);

        verify(mailSender, times(1)).send(expectedMailMessage);
    }
}