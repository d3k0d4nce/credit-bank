package ru.kishko.dossier.services;

import ru.kishko.openapi.model.EmailMessage;

public interface EmailService {
    void sendMessage(EmailMessage emailMessage);
}
