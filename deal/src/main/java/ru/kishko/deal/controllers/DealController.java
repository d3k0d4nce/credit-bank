package ru.kishko.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.deal.services.DealService;
import ru.kishko.openapi.api.DealApi;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    private final DealService dealService;

    @Override
    public ResponseEntity<Void> dealCalculateStatementIdPost(String statementId, FinishRegistrationRequestDto request) {
        log.info("Received request to calculate loan for statementId: {} with request: {}", statementId, request);
        dealService.calculateLoan(statementId, request);
        log.info("Loan calculated successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> dealOfferSelectPost(LoanOfferDto request) {
        log.info("Received request to select loan offer: {}", request);
        dealService.selectLoanOffer(request);
        log.info("Loan offer selected successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> dealStatementPost(LoanStatementRequestDto request) {
        log.info("Received request for loan offers: {}", request);
        List<LoanOfferDto> offers = dealService.getLoanOffers(request);
        log.info("Returning loan offers: {}", offers);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendRequestForDocument(String statementId) {
        dealService.sendRequestForDocument(statementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendRequestForSignDocument(String statementId) {
        dealService.updateApplicationSesCode(statementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signDocument(String statementId, Integer code) {
        dealService.verifySesCode(statementId, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> dealDossierStatementGet(String statementId) {
        Integer result = dealService.dealDossierStatementGet(statementId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> dealDossierStatementStatusPut(String statementId) {
        dealService.updateApplicationStatus(statementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}