package ru.kishko.calculator.services;

import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.api.dtos.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorOfferService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request);
}
