package ru.kishko.dossier.strategy;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Service
public class CreditIssued extends AbstractEmailSender {

    public CreditIssued(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    protected String getEmailText(EmailMessage emailMessage) {
        return "Здравствуйте, документы подписаны, кредит одобрен!";
    }

    @Override
    public Theme getTheme() {
        return Theme.CREDIT_ISSUED;
    }
}