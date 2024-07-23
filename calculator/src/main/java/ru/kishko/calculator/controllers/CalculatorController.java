package ru.kishko.calculator.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.kishko.calculator.services.CalculatorCreditService;
import ru.kishko.calculator.services.CalculatorOfferService;
import ru.kishko.openapi.api.CalculatorApi;
import ru.kishko.openapi.model.CreditDto;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.openapi.model.ScoringDataDto;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CalculatorController implements CalculatorApi {

    private final CalculatorOfferService calculatorService;
    private final CalculatorCreditService creditService;

    @Override
    public ResponseEntity<CreditDto> calculatorCalcPost(ScoringDataDto request) {
        log.info("Received credit request: {}", request);
        return new ResponseEntity<>(creditService.calculateCredit(request), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculatorOffersPost(LoanStatementRequestDto request) {
        log.info("Received loan offer request: {}", request);
        return new ResponseEntity<>(calculatorService.calculateLoanOffers(request), HttpStatus.OK);
    }
}
