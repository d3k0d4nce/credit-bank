package ru.kishko.deal.services;

public interface KafkaService {
    void sendToFinishRegistrationTopic(String statementId);

    void sendToCreateDocumentsTopic(String statementId);

    void sendToSendSesTopic(String statementId);

    void sendToSendDocumentsTopic(String statementId);

    void sendToCreditIssuedTopic(String statementId);

    void sendToApplicationDeniedTopic(String statementId);
}
