package ru.kishko.dossier.strategy;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Service
public class FinishRegistration extends AbstractEmailSender {

    public FinishRegistration(JavaMailSender mailSender) {
        super(mailSender);
    }

    @Override
    protected String getEmailText(EmailMessage emailMessage) {
        return "Здравствуйте, завершите оформление.";
    }

    @Override
    public Theme getTheme() {
        return Theme.FINISH_REGISTRATION;
    }
}