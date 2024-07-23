package ru.kishko.statement.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.statement.exceptions.validators.AgeValidator;
import ru.kishko.statement.utils.FeignControllerClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private AgeValidator ageValidator;

    @Mock
    private FeignControllerClient feignControllerClient;

    @InjectMocks
    private StatementServiceImpl statementService;

    @Test
    void statementOfferPost_shouldCallFeignClient() {
        LoanOfferDto request = new LoanOfferDto();

        statementService.statementOfferPost(request);

        verify(feignControllerClient).dealOfferSelectPost(request);
    }

    @Test
    void statementPost_shouldValidateRequestAndCallFeignClient() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        List<LoanOfferDto> expectedOffers = List.of(new LoanOfferDto(), new LoanOfferDto(), new LoanOfferDto(), new LoanOfferDto());

        Mockito.when(feignControllerClient.dealStatementPost(request)).thenReturn(expectedOffers);

        List<LoanOfferDto> actualOffers = statementService.statementPost(request);

        assertEquals(expectedOffers, actualOffers);
        verify(ageValidator).validate(request);
        verify(feignControllerClient).dealStatementPost(request);
    }
}