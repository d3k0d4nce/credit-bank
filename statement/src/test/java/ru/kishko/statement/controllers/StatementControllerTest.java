package ru.kishko.statement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.statement.services.StatementService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatementController.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementService statementService;

    @Test
    void statementOfferPost_shouldReturnOk() throws Exception {
        LoanOfferDto loanOfferDto = createLoanOfferDto();

        doNothing().when(statementService).statementOfferPost(loanOfferDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(loanOfferDto)))
                .andExpect(status().isOk());
    }

    private LoanOfferDto createLoanOfferDto() {
        LoanOfferDto loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(UUID.randomUUID());
        loanOfferDto.setRequestedAmount(new BigDecimal("100000"));
        loanOfferDto.setTotalAmount(new BigDecimal("110000"));
        loanOfferDto.setTerm(12);
        loanOfferDto.setMonthlyPayment(new BigDecimal("10000"));
        loanOfferDto.setRate(new BigDecimal("10"));
        loanOfferDto.setIsInsuranceEnabled(true);
        loanOfferDto.setIsSalaryClient(true);
        return loanOfferDto;
    }

    private String objectToJson(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}