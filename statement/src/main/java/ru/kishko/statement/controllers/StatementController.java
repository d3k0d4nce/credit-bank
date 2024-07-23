package ru.kishko.statement.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.kishko.openapi.api.StatementApi;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.statement.services.StatementService;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class StatementController implements StatementApi {

    private final StatementService statementService;

    @Override
    public ResponseEntity<Void> statementOfferPost(LoanOfferDto loanOfferDto) {
        log.info("statementOfferPost() loanOfferDTO = {}", loanOfferDto);
        statementService.statementOfferPost(loanOfferDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> statementPost(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("statementPost() loanStatementRequestDto = {}", loanStatementRequestDto);
        return new ResponseEntity<>(statementService.statementPost(loanStatementRequestDto), HttpStatus.OK);
    }
}
