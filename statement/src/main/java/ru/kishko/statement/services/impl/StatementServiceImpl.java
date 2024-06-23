package ru.kishko.statement.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.statement.exceptions.validators.AgeValidator;
import ru.kishko.statement.services.StatementService;
import ru.kishko.statement.utils.FeignControllerClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final AgeValidator ageValidator;
    private final FeignControllerClient feignControllerClient;

    @Override
    public void statementOfferPost(LoanOfferDto request) {
        log.info("dealOfferSelectPost() loanOfferDTO = {}", request);
        feignControllerClient.dealOfferSelectPost(request);
    }

    @Override
    public List<LoanOfferDto> statementPost(LoanStatementRequestDto request) {
        ageValidator.validate(request);
        log.info("statementPost() loanStatementRequestDto = {}", request);
        return feignControllerClient.dealStatementPost(request);
    }
}
