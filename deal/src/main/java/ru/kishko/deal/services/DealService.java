package ru.kishko.deal.services;

import ru.kishko.api.dtos.FinishRegistrationRequestDto;
import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.api.dtos.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request);

    void selectLoanOffer(LoanOfferDto request);

    void calculateLoan(String statementId, FinishRegistrationRequestDto request);
}
