package ru.kishko.dossier.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kishko.dossier.services.EmailService;
import ru.kishko.openapi.model.EmailMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailMessageListener {

    private final EmailService emailService;

    @KafkaListener(groupId = "${spring.kafka.common-group-id}",
            topics = {"finish-registration",
                    "create-documents",
                    "send-documents",
                    "send-ses",
                    "credit-issued"},
            containerFactory = "commonKafkaListenerContainerFactory")
    public void commonListener(EmailMessage emailMessage) {
        log.info("Сообщение от deal: {}", emailMessage);
        emailService.sendMessage(emailMessage);
        log.info("Письмо отправлено");
    }
}
