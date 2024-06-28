package ru.kishko.deal.services;

import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request);

    void selectLoanOffer(LoanOfferDto request);

    void calculateLoan(String statementId, FinishRegistrationRequestDto request);

    void sendRequestForDocument(String statementId);

    void updateApplicationSesCode(String statementId);

    void verifySesCode(String statementId, Integer code);
}