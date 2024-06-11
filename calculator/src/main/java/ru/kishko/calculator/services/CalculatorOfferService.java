package ru.kishko.calculator.services;

import ru.kishko.calculator.dtos.LoanOfferDto;
import ru.kishko.calculator.dtos.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorOfferService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request);
}