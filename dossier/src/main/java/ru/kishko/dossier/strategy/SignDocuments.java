package ru.kishko.dossier.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.dossier.utils.FeignControllerClient;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Slf4j
@Service
public class SignDocuments extends AbstractEmailSender {

    private final FeignControllerClient feignControllerClient;

    @Autowired
    public SignDocuments(JavaMailSender mailSender, FeignControllerClient feignControllerClient) {
        super(mailSender);
        this.feignControllerClient = feignControllerClient;
    }

    @Override
    protected String getEmailText(EmailMessage emailMessage) {
        Integer sesCode = feignControllerClient.getSesCodeByStatementId(String.valueOf(emailMessage.getStatementId()));
        return "Здравствуйте, ваш код для подписания документов: " + sesCode;
    }

    @Override
    public Theme getTheme() {
        return Theme.SIGN_DOCUMENTS_WITH_SES_CODE;
    }
}