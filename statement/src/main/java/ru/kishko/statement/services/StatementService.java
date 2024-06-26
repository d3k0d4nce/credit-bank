package ru.kishko.statement.services;

import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    void statementOfferPost(LoanOfferDto loanOfferDto);

    List<LoanOfferDto> statementPost(LoanStatementRequestDto loanStatementRequestDto);
}
