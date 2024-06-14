package ru.kishko.calculator.services;

import ru.kishko.api.dtos.CreditDto;
import ru.kishko.api.dtos.ScoringDataDto;

public interface CalculatorCreditService {
    CreditDto calculateCredit(ScoringDataDto request);
}
