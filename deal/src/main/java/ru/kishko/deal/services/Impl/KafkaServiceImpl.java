package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.services.KafkaService;
import ru.kishko.deal.services.StatementService;
import ru.kishko.openapi.model.EmailMessage;
import ru.kishko.openapi.model.Theme;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final StatementService statementService;
    private final KafkaTemplate<Integer, EmailMessage> emailKafkaTemplate;

    @Value("${kafka.topic.finishRegistration}")
    private String finishRegistrationTopic;

    @Value("${kafka.topic.createDocuments}")
    private String createDocumentsTopic;

    @Value("${kafka.topic.sendDocuments}")
    private String sendDocumentsTopic;

    @Value("${kafka.topic.sendSes}")
    private String sendSesTopic;

    @Value("${kafka.topic.creditIssued}")
    private String creditIssuedTopic;

    @Value("${kafka.topic.statementDenied}")
    private String statementDeniedTopic;

    @Override
    public void sendToFinishRegistrationTopic(String statementId) {
        log.info("Sending message to finishRegistrationTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.FINISH_REGISTRATION);
        emailKafkaTemplate.send(finishRegistrationTopic, emailMessage);
    }

    @Override
    public void sendToCreateDocumentsTopic(String statementId) {
        log.info("Sending message to createDocumentsTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.CREATE_DOCUMENT);
        emailKafkaTemplate.send(createDocumentsTopic, emailMessage);
    }

    @Override
    public void sendToSendSesTopic(String statementId) {
        log.info("Sending message to sendSesTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.SIGN_DOCUMENTS_WITH_SES_CODE);
        emailKafkaTemplate.send(sendSesTopic, emailMessage);
    }

    @Override
    public void sendToSendDocumentsTopic(String statementId) {
        log.info("Sending message to sendDocumentsTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.YOUR_LOAN_DOCUMENTS);
        emailKafkaTemplate.send(sendDocumentsTopic, emailMessage);
    }

    @Override
    public void sendToCreditIssuedTopic(String statementId) {
        log.info("Sending message to creditIssuedTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.CREDIT_ISSUED);
        emailKafkaTemplate.send(creditIssuedTopic, emailMessage);
    }

    @Override
    public void sendToApplicationDeniedTopic(String statementId) {
        log.info("Sending message to statementDeniedTopic for statementId: {}", statementId);
        EmailMessage emailMessage = createEmailMessage(statementId, Theme.APPLICATION_DENIED);
        emailKafkaTemplate.send(statementDeniedTopic, emailMessage);
    }

    private EmailMessage createEmailMessage(String statementId, Theme theme) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        String email = statement.getClient().getEmail();
        return EmailMessage.builder()
                .address(email)
                .statementId(UUID.fromString(statementId))
                .theme(theme)
                .build();
    }
}