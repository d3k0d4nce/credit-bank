package ru.kishko.dossier.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.kishko.dossier.utils.FeignControllerClient;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignDocumentsTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private FeignControllerClient feignControllerClient;

    @InjectMocks
    private SignDocuments signDocuments;

    @Test
    void sendMail() {
        EmailMessage emailMessage = new EmailMessage("test@example.com", Theme.SIGN_DOCUMENTS_WITH_SES_CODE, UUID.randomUUID());
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo("test@example.com");
        expectedMailMessage.setSubject(Theme.SIGN_DOCUMENTS_WITH_SES_CODE.name());

        when(feignControllerClient.getSesCodeByStatementId(any(String.class))).thenReturn(111111);

        expectedMailMessage.setText("Здравствуйте, ваш код для подписания документов: " + 111111);

        signDocuments.sendMail(emailMessage);

        verify(mailSender, times(1)).send(expectedMailMessage);
    }
}