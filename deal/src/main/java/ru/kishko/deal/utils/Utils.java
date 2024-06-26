package ru.kishko.deal.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.mappers.ScoringDataMapper;
import ru.kishko.openapi.model.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Utils {

    private final FeignControllerClient feignControllerClient;
    private final ScoringDataMapper scoringDataMapper;

    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request) {
        log.info("Fetching loan offers from external service.");
        return feignControllerClient.getLoanOffers(request);
    }

    public List<LoanOfferDto> getUpdatedLoanOffers(LoanStatementRequestDto request,
                                                   UUID statementId) {
        log.info("Updating loan offers with statementId: {}", statementId);
        List<LoanOfferDto> loanOffers = getLoanOffers(request);

        for (LoanOfferDto offer : loanOffers) {
            offer.setStatementId(statementId);
        }

        log.info("Updated loan offers: {}", loanOffers);
        return loanOffers;
    }

    public ScoringDataDto makeScoringDataDto(Statement statement, FinishRegistrationRequestDto request) {
        log.info("Creating scoring data DTO.");
        return scoringDataMapper.toScoringDataDto(request, statement);
    }

    public CreditDto calculateCredit(ScoringDataDto request) {
        log.info("Calculating credit with scoring data: {}", request);
        return feignControllerClient.calculate(request);
    }
}
