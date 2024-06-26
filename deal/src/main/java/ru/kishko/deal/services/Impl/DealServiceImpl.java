package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kishko.deal.exceptions.validators.PassportIssueDateValidator;
import ru.kishko.deal.services.DealService;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.services.ClientService;
import ru.kishko.deal.services.CreditService;
import ru.kishko.deal.services.StatementService;
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
    }

    @Override
    @Transactional
    public void calculateLoan(String statementId, FinishRegistrationRequestDto request) {
        passportIssueDateValidator.validate(request);
        log.info("Calculating loan for statementId: {} with request: {}", statementId, request);
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        ScoringDataDto scoringData = utils.makeScoringDataDto(statement, request);
        log.info("Scoring data created: {}", scoringData);
        CreditDto credit = utils.calculateCredit(scoringData);
        log.info("Credit data calculated: {}", credit);
        creditService.createCredit(credit);
        statementService.updateStatusAndStatusHistory(statement);
        log.info("Loan calculated and credit created successfully.");
    }
}
