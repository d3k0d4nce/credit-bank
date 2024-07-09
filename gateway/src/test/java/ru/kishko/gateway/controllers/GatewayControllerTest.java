package ru.kishko.gateway.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kishko.gateway.utils.FeignDealClient;
import ru.kishko.gateway.utils.FeignStatementClient;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GatewayControllerTest {

    @InjectMocks
    private GatewayController gatewayController;

    @Mock
    private FeignStatementClient feignStatementClient;

    @Mock
    private FeignDealClient feignDealClient;

    @Test
    public void testGatewayCalculateStatementIdPost() {
        String statementId = "1234567890";
        FinishRegistrationRequestDto requestDto = new FinishRegistrationRequestDto();

        gatewayController.gatewayCalculateStatementIdPost(statementId, requestDto);

        verify(feignDealClient, times(1)).dealCalculateStatementIdPost(statementId, requestDto);
    }

    @Test
    public void testGatewayOfferPost() {
        LoanOfferDto offerDto = new LoanOfferDto();

        gatewayController.gatewayOfferPost(offerDto);

        verify(feignStatementClient, times(1)).applyOffer(offerDto);
    }

    @Test
    public void testGatewayStatementPost() {
        LoanStatementRequestDto requestDto = new LoanStatementRequestDto();
        List<LoanOfferDto> offers = List.of(new LoanOfferDto());
        Mockito.when(feignStatementClient.getLoanOffers(any(LoanStatementRequestDto.class))).thenReturn(offers);

        ResponseEntity<List<LoanOfferDto>> response = gatewayController.gatewayStatementPost(requestDto);

        verify(feignStatementClient, times(1)).getLoanOffers(requestDto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
    }

    @Test
    public void testSendRequestForDocument() {
        String statementId = "1234567890";

        gatewayController.sendRequestForDocument(statementId);

        verify(feignDealClient, times(1)).sendRequestForDocument(statementId);
    }

    @Test
    public void testSendRequestForSignDocument() {
        String statementId = "1234567890";

        gatewayController.sendRequestForSignDocument(statementId);

        verify(feignDealClient, times(1)).sendRequestForSignDocument(statementId);
    }

    @Test
    public void testSignDocument() {
        String statementId = "1234567890";
        Integer code = 12345;

        gatewayController.signDocument(statementId, code);

        verify(feignDealClient, times(1)).signDocument(statementId, code);
    }
}