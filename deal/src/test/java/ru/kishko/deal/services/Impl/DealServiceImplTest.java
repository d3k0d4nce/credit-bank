package ru.kishko.deal.services.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.exceptions.validators.AgeValidator;
import ru.kishko.deal.exceptions.validators.PassportIssueDateValidator;
import ru.kishko.deal.services.ClientService;
import ru.kishko.deal.services.CreditService;
import ru.kishko.deal.services.StatementService;
import ru.kishko.deal.utils.Utils;
import ru.kishko.openapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private ClientService clientService;

    @Mock
    private StatementService statementService;

    @Mock
    private CreditService creditService;

    @Mock
    private PassportIssueDateValidator passportIssueDateValidator;

    @Mock
    private AgeValidator ageValidator;

    @Mock
    private Utils utils;

    @InjectMocks
    private DealServiceImpl dealService;

    UUID uuid;
    Client expectedClient;
    Statement expectedStatement;

    @BeforeEach
    void setUp() {

        uuid = UUID.randomUUID();

        expectedClient = Client.builder()
                .email("kishko.2003@list.ru")
                .build();

        expectedStatement = Statement.builder()
                .statementId(uuid)
                .client(expectedClient)
                .build();

    }

    @Test
    void getLoanOffers() {

        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .email("kishko.2003@list.ru")
                .build();

        List<LoanOfferDto> expectedResponse = new ArrayList<>(
                List.of(
                        new LoanOfferDto().statementId(uuid),
                        new LoanOfferDto().statementId(uuid),
                        new LoanOfferDto().statementId(uuid),
                        new LoanOfferDto().statementId(uuid)
                )
        );

        when(clientService.createClient(any(LoanStatementRequestDto.class))).thenReturn(expectedClient);
        when(statementService.createStatement(any(Client.class))).thenReturn(expectedStatement);

        Client actualClient = clientService.createClient(request);
        Statement actualStatement = statementService.createStatement(expectedClient);

        when(utils.getUpdatedLoanOffers(any(LoanStatementRequestDto.class), any(UUID.class))).thenReturn(expectedResponse);

        List<LoanOfferDto> actualResponse = dealService.getLoanOffers(request);

        assertEquals(actualClient, expectedClient);
        assertEquals(actualStatement, expectedStatement);
        assertEquals(actualResponse, expectedResponse);
        verify(clientService, times(2)).createClient(request);
        verify(statementService, times(2)).createStatement(expectedClient);
        verify(utils, times(1)).getUpdatedLoanOffers(request, expectedStatement.getStatementId());
    }

    @Test
    void selectLoanOffer() {
        LoanOfferDto request = LoanOfferDto.builder()
                .statementId(uuid)
                .build();

        when(statementService.getStatementById(any(UUID.class))).thenReturn(expectedStatement);
        doNothing().when(statementService).updateStatement(expectedStatement, request);

        dealService.selectLoanOffer(request);

        verify(statementService, times(1)).getStatementById(uuid);
        verify(statementService, times(1)).updateStatement(expectedStatement, request);
    }

    @Test
    void calculateLoan() {

        FinishRegistrationRequestDto request = FinishRegistrationRequestDto
                .builder()
                .accountNumber("52")
                .build();

        ScoringDataDto expectedScoringData = ScoringDataDto.builder()
                .accountNumber("52")
                .build();

        CreditDto expectedCredit = CreditDto.builder()
                .build();

        when(statementService.getStatementById(any(UUID.class))).thenReturn(expectedStatement);
        when(utils.makeScoringDataDto(any(Statement.class), any(FinishRegistrationRequestDto.class)))
                .thenReturn(expectedScoringData);
        when(utils.calculateCredit(any(ScoringDataDto.class))).thenReturn(expectedCredit);
        when(creditService.createCredit(any(CreditDto.class))).thenReturn(new Credit());
        doNothing().when(statementService).updateStatusAndStatusHistory(expectedStatement);

        dealService.calculateLoan(uuid.toString(), request);

        verify(statementService, times(1)).getStatementById(uuid);
        verify(utils, times(1)).makeScoringDataDto(expectedStatement, request);
        verify(utils, times(1)).calculateCredit(any(ScoringDataDto.class));
        verify(creditService, times(1)).createCredit(expectedCredit);
        verify(statementService, times(1)).updateStatusAndStatusHistory(expectedStatement);

    }
}