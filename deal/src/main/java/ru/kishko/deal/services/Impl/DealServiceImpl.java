package ru.kishko.deal.services.Impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.exceptions.validators.PassportIssueDateValidator;
import ru.kishko.deal.services.*;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.utils.Utils;
import ru.kishko.openapi.model.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CreditService creditService;
    private final PassportIssueDateValidator passportIssueDateValidator;
    private final Utils utils;
    private final KafkaService kafkaService;

    @Override
    @Transactional
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request) {
        log.info("Getting loan offers for request: {}", request);
        Client client = clientService.createClient(request);
        Statement statement = statementService.createStatement(client);
        List<LoanOfferDto> offers = utils.getUpdatedLoanOffers(request, statement.getStatementId());
        log.info("Returning loan offers: {}", offers);
        return offers;
    }

    @Override
    public void selectLoanOffer(LoanOfferDto request) {
        log.info("Selecting loan offer: {}", request);
        Statement statement = statementService.getStatementById(request.getStatementId());
        statementService.updateStatement(statement, request);
        log.info("Loan offer selected.");
        kafkaService.sendToFinishRegistrationTopic(String.valueOf(statement.getStatementId()));
    }

    @Override
    @Transactional
    public void calculateLoan(String statementId, FinishRegistrationRequestDto request) {
        passportIssueDateValidator.validate(request);
        log.info("Calculating loan for statementId: {} with request: {}", statementId, request);
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        ScoringDataDto scoringData = utils.makeScoringDataDto(statement, request);
        log.info("Scoring data created: {}", scoringData);
        CreditDto creditDto;
        try {
            creditDto = utils.calculateCredit(scoringData);
        } catch (FeignException e) {
            statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
            log.error("Error calculating credit for statementId: {}", statementId, e);
            throw e;
        }
        log.info("Credit data calculated: {}", creditDto);
        Credit credit = creditService.createCredit(creditDto);
        log.info("Credit created: {}", credit);
        statement = statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        statementService.updateStatementByCreditInfo(statement, credit);
        clientService.updateClientFromRequest(statement, request);
        log.info("Loan calculated and credit created successfully.");
        kafkaService.sendToCreateDocumentsTopic(statementId);
    }

    @Override
    public void sendRequestForDocument(String statementId) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        statementService.updateStatusAndStatusHistory(statement,
                ApplicationStatus.PREPARE_DOCUMENTS, ChangeType.AUTOMATIC);
        kafkaService.sendToSendDocumentsTopic(statementId);
    }

    @Override
    public void updateApplicationSesCode(String statementId) {
        statementService.updateStatementSesCode(statementId);
        kafkaService.sendToSendSesTopic(statementId);
    }

    @Override
    public void verifySesCode(String statementId, Integer code){
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        Integer sesCode = statement.getSesCode();
        if(sesCode.equals(code)){
            statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.DOCUMENT_SIGNED,
                    ChangeType.AUTOMATIC);
            statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.CREDIT_ISSUED,
                    ChangeType.AUTOMATIC);
            kafkaService.sendToCreditIssuedTopic(statementId);
        }
    }

    @Override
    public Integer dealDossierStatementGet(String statementId) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        return statement.getSesCode();
    }

    @Override
    public void updateApplicationStatus(String statementId) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.DOCUMENT_CREATED,
                ChangeType.AUTOMATIC);
    }
}