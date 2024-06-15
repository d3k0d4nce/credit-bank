package ru.kishko.calculator.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kishko.calculator.exceptions.validators.AgeValidator;
import ru.kishko.calculator.services.CalculatorOfferService;
import ru.kishko.calculator.services.utils.LoanCalculator;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorOfferServiceImpl implements CalculatorOfferService {

    @Value("${base.interest.rate}")
    private BigDecimal BASE_INTEREST_RATE;
    private final AgeValidator ageValidator;
    private final LoanCalculator loanCalculator;

    @Override
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto request) {
        ageValidator.validate(request);
        log.info("Starting calculation of loan offers for request: {}", request);
        return Stream.of(true, false)
                .flatMap(isSalaryClient -> Stream.of(true, false)
                        .map(isInsuranceEnabled -> createLoanOffer(request, isSalaryClient, isInsuranceEnabled)))
                .sorted(Comparator.comparing(LoanOfferDto::getRate).reversed())
                .collect(Collectors.toList());
    }

    private LoanOfferDto createLoanOffer(LoanStatementRequestDto loanStatement, Boolean isSalaryClient, Boolean isInsuranceEnabled) {
        log.debug("Creating loan offer with parameters: isSalaryClient={}, isInsuranceEnabled={}", isSalaryClient, isInsuranceEnabled);

        BigDecimal loanAmount = loanStatement.getAmount();

        BigDecimal interestRate = loanCalculator.adjustInterestRate(BASE_INTEREST_RATE, isInsuranceEnabled, isSalaryClient);

        BigDecimal principal = loanCalculator.calculatePrincipal(loanAmount, isInsuranceEnabled);
        BigDecimal monthlyPayment = loanCalculator.calculateMonthlyPayment(principal, interestRate, loanStatement.getTerm());
        BigDecimal totalAmount = loanCalculator.calculateTotalAmount(monthlyPayment, loanStatement.getTerm());

        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .term(loanStatement.getTerm())
                .requestedAmount(loanAmount)
                .monthlyPayment(monthlyPayment)
                .rate(interestRate)
                .totalAmount(totalAmount)
                .isSalaryClient(isSalaryClient)
                .isInsuranceEnabled(isInsuranceEnabled)
                .build();
    }
}
