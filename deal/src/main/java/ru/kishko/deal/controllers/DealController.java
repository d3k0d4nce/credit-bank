package ru.kishko.deal.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.api.dtos.FinishRegistrationRequestDto;
import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.api.dtos.LoanStatementRequestDto;
import ru.kishko.deal.services.DealService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    // 2.4.1. Расчет возможных условий кредита
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(@RequestBody @Valid LoanStatementRequestDto request) {
        log.info("Received request for loan offers: {}", request);
        List<LoanOfferDto> offers = dealService.getLoanOffers(request);
        log.info("Returning loan offers: {}", offers);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    // 2.4.2. Выбор одного из предложений
    @PostMapping("/offer/select")
    public void selectLoanOffer(@RequestBody @Valid LoanOfferDto request) {
        log.info("Received request to select loan offer: {}", request);
        dealService.selectLoanOffer(request);
        log.info("Loan offer selected successfully.");
    }

    // 2.4.3. Завершение регистрации + полный подсчёт кредита
    @PostMapping("/calculate/{statementId}")
    public void calculateLoan(@PathVariable("statementId") String statementId, @RequestBody @Valid FinishRegistrationRequestDto request) {
        log.info("Received request to calculate loan for statementId: {} with request: {}", statementId, request);
        dealService.calculateLoan(statementId, request);
        log.info("Loan calculated successfully.");
    }

}
