package ru.kishko.dossier.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kishko.dossier.services.EmailService;
import ru.kishko.dossier.strategy.EmailSendStrategy;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final Map<Theme, EmailSendStrategy> strategies;

    @Autowired
    public EmailServiceImpl(List<EmailSendStrategy> strategies) {
        this.strategies = strategies.stream().collect(
                Collectors.toMap(EmailSendStrategy::getTheme, Function.identity())
        );
    }

    @Override
    public void sendMessage(EmailMessage emailMessage) {
        log.info("Sending email message: {}", emailMessage);
        EmailSendStrategy strategy = findStrategy(emailMessage.getTheme());
        strategy.sendMail(emailMessage);
        log.info("Email sent successfully.");

    }

    private EmailSendStrategy findStrategy(Theme theme) {
        return strategies.get(theme);
    }
}
