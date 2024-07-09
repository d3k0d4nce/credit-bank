package ru.kishko.dossier.strategy;

import org.springframework.stereotype.Component;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

@Component
public interface EmailSendStrategy {

    void sendMail(EmailMessage emailMessage);

    Theme getTheme();

}
