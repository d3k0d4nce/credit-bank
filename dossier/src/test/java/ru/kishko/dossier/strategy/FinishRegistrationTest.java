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
class FinishRegistrationTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private FinishRegistration finishRegistration;

    @Test
    void sendMail() {
        EmailMessage emailMessage = new EmailMessage("test@example.com", Theme.FINISH_REGISTRATION, UUID.randomUUID());
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo("test@example.com");
        expectedMailMessage.setSubject(Theme.FINISH_REGISTRATION.name());
        expectedMailMessage.setText("Здравствуйте, завершите оформление.");

        finishRegistration.sendMail(emailMessage);

        verify(mailSender, times(1)).send(expectedMailMessage);
    }
}