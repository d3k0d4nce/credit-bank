package ru.kishko.deal.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilsTest {

    @Mock
    private FeignControllerClient feignControllerClient;

    @InjectMocks
    private Utils utils;

    LoanStatementRequestDto loanStatementRequestDto;
    LoanOfferDto loanOfferDto;
    Statement statement;
    ScoringDataDto scoringDataDto;
    CreditDto creditDto;
    UUID statementId;

    @BeforeEach
    void setUp() {
        statementId = UUID.randomUUID();
        loanStatementRequestDto = new LoanStatementRequestDto();
        loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(statementId);
        statement = new Statement();
        statement.setStatementId(statementId);
        statement.setAppliedOffer(loanOfferDto);
        scoringDataDto = new ScoringDataDto();
        creditDto = new CreditDto();
    }

    @Test
    void getLoanOffers() {
        List<LoanOfferDto> expectedOffers = Collections.singletonList(loanOfferDto);
        when(feignControllerClient.getLoanOffers(loanStatementRequestDto)).thenReturn(expectedOffers);

        List<LoanOfferDto> actualOffers = utils.getLoanOffers(loanStatementRequestDto);

        assertThat(actualOffers).isEqualTo(expectedOffers);
        verify(feignControllerClient, times(1)).getLoanOffers(loanStatementRequestDto);
    }

    @Test
    void getUpdatedLoanOffers() {
        List<LoanOfferDto> expectedOffers = Collections.singletonList(loanOfferDto);
        when(feignControllerClient.getLoanOffers(loanStatementRequestDto)).thenReturn(expectedOffers);

        List<LoanOfferDto> actualOffers = utils.getUpdatedLoanOffers(loanStatementRequestDto, statementId);

        assertThat(actualOffers).isEqualTo(expectedOffers);
        verify(feignControllerClient, times(1)).getLoanOffers(loanStatementRequestDto);
        for (LoanOfferDto offer : actualOffers) {
            assertThat(offer.getStatementId()).isEqualTo(statementId);
        }
    }

    @Test
    void calculateCredit() {
        when(feignControllerClient.calculate(scoringDataDto)).thenReturn(creditDto);

        CreditDto actualCreditDto = utils.calculateCredit(scoringDataDto);

        assertThat(actualCreditDto).isEqualTo(creditDto);
        verify(feignControllerClient, times(1)).calculate(scoringDataDto);
    }
}