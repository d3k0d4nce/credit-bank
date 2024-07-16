package ru.kishko.gateway.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.kishko.gateway.utils.FeignDealClient;
import ru.kishko.gateway.utils.FeignStatementClient;
import ru.kishko.openapi.api.GatewayApi;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class GatewayController implements GatewayApi {

    private final FeignStatementClient feignStatementClient;
    private final FeignDealClient feignDealClient;

    @Override
    public ResponseEntity<Void> gatewayCalculateStatementIdPost(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("Запрос на расчет statementId: {}, request: {}", statementId, finishRegistrationRequestDto);
        feignDealClient.dealCalculateStatementIdPost(statementId, finishRegistrationRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> gatewayOfferPost(LoanOfferDto loanOfferDto) {
        log.info("Запрос на предложение по кредиту: {}", loanOfferDto);
        feignStatementClient.applyOffer(loanOfferDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> gatewayStatementPost(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Запрос на получение предложений по кредиту: {}", loanStatementRequestDto);
        List<LoanOfferDto> result = feignStatementClient.getLoanOffers(loanStatementRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendRequestForDocument(String statementId) {
        log.info("Запрос на отправку документа: {}", statementId);
        feignDealClient.sendRequestForDocument(statementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendRequestForSignDocument(String statementId) {
        log.info("Запрос на отправку документа для подписи: {}", statementId);
        feignDealClient.sendRequestForSignDocument(statementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signDocument(String statementId, Integer code) {
        log.info("Запрос на подписание документа: statementId: {}, code: {}", statementId, code);
        feignDealClient.signDocument(statementId, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
