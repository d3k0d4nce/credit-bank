package ru.kishko.calculator.services;

import ru.kishko.openapi.model.CreditDto;
import ru.kishko.openapi.model.ScoringDataDto;

public interface CalculatorCreditService {
    CreditDto calculateCredit(ScoringDataDto request);
}
