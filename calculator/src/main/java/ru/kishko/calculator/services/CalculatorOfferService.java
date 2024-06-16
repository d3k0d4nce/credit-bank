package ru.kishko.calculator.services;

import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorOfferService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request);
}
