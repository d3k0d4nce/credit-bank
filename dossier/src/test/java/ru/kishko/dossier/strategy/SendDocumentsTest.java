package ru.kishko.dossier.strategy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.kishko.dossier.files.FileService;
import ru.kishko.dossier.utils.FeignControllerClient;
import ru.kishko.openapi.model.EmailMessage;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendDocumentsTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private FileService fileService;

    @Mock
    private FeignControllerClient feignControllerClient;

    @Mock
    private AbstractEmailSender abstractEmailSender;

    @InjectMocks
    private SendDocuments sendDocuments;

    @Disabled
    void sendMail() throws Exception {
        // Arrange
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setAddress("test@example.com");
        emailMessage.setStatementId(UUID.randomUUID());

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(fileService.createCreditFile(Mockito.anyString())).thenReturn(new File("testfile.txt"));
        when(abstractEmailSender.fromAddress).thenReturn("bebriki.2024@mail.ru");

        // Act
        sendDocuments.sendMail(emailMessage);

        // Assert
        Mockito.verify(mailSender).send(mimeMessage);
        Mockito.verify(feignControllerClient).updateApplicationStatus("123");
    }
}