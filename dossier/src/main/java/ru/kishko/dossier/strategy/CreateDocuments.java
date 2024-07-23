package ru.kishko.dossier.strategy;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Service
public class CreateDocuments extends AbstractEmailSender {

    public CreateDocuments(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    protected String getEmailText(EmailMessage emailMessage) {
        return "Здравствуйте, ваша заявка одобрена, ожидайте отправки документов для подписания";
    }

    @Override
    public Theme getTheme() {
        return Theme.CREATE_DOCUMENT;
    }
}
