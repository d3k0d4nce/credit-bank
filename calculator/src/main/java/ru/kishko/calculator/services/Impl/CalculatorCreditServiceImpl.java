package ru.kishko.calculator.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kishko.calculator.exceptions.validators.PassportIssueDateValidator;
import ru.kishko.calculator.services.CalculatorCreditService;
import ru.kishko.calculator.services.utils.LoanCalculator;
import ru.kishko.calculator.services.utils.UserValidator;
import ru.kishko.openapi.model.CreditDto;
import ru.kishko.openapi.model.PaymentScheduleElementDto;
import ru.kishko.openapi.model.ScoringDataDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorCreditServiceImpl implements CalculatorCreditService {

    @Value("${base.interest.rate}")
    private BigDecimal BASE_INTEREST_RATE;
    private final UserValidator userValidator;
    private final LoanCalculator loanCalculator;
    private final PassportIssueDateValidator passportIssueDateValidator;

    @Override
    public CreditDto calculateCredit(ScoringDataDto request) {
        passportIssueDateValidator.validate(request);
        log.info("Starting credit calculation for request: {}", request); // Логирование начала расчета
        return createCredit(request);
    }

    private CreditDto createCredit(ScoringDataDto scoringData) {
        log.debug("Creating credit object from scoring data: {}", scoringData); // Логирование создания CreditDto

        CreditDto creditDto = CreditDto.builder()
                .amount(scoringData.getAmount())
                .rate(BASE_INTEREST_RATE)
                .term(scoringData.getTerm())
                .isSalaryClient(scoringData.getIsSalaryClient())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .build();

        userValidator.validate(scoringData, creditDto);

        BigDecimal interestRate = loanCalculator.adjustInterestRate(creditDto.getRate(), creditDto.getIsInsuranceEnabled(), creditDto.getIsSalaryClient());
        BigDecimal principal = loanCalculator.calculatePrincipal(creditDto.getAmount(), creditDto.getIsInsuranceEnabled());
        BigDecimal monthlyPayment = loanCalculator.calculateMonthlyPayment(principal, interestRate, creditDto.getTerm());
        BigDecimal totalAmount = loanCalculator.calculateTotalAmount(monthlyPayment, creditDto.getTerm());

        creditDto.setAmount(principal);
        creditDto.setMonthlyPayment(monthlyPayment);
        creditDto.setRate(interestRate);
        creditDto.setPsk(totalAmount);
        creditDto.setPaymentSchedule(calculatePaymentSchedule(creditDto));

        log.info("Credit calculation completed: {}", creditDto); // Логирование завершения расчета
        return creditDto;
    }

    private List<PaymentScheduleElementDto> calculatePaymentSchedule(CreditDto creditDto) {
        log.debug("Calculating payment schedule for credit: {}", creditDto); // Логирование начала расчета графика платежей

        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        BigDecimal remainingDebt = creditDto.getAmount();
        BigDecimal totalPayment = creditDto.getMonthlyPayment();
        BigDecimal interestPayment;
        BigDecimal debtPayment;
        BigDecimal rate = creditDto.getRate();
        Integer term = creditDto.getTerm();

        for (int i = 1; i <= term; i++) {
            startDate = startDate.plusMonths(1);
            interestPayment = calculateInterestPayment(remainingDebt, rate);
            debtPayment = calculateDebtPayment(totalPayment, interestPayment);
            remainingDebt = calculateRemainingDebt(remainingDebt, debtPayment);

            if (i == term) {
                totalPayment = totalPayment.add(remainingDebt);
                remainingDebt = BigDecimal.valueOf(0);
            }

            paymentSchedule.add(
                    PaymentScheduleElementDto.builder()
                            .number(i)
                            .date(startDate)
                            .totalPayment(totalPayment.setScale(2, RoundingMode.HALF_UP))
                            .interestPayment(interestPayment.setScale(2, RoundingMode.HALF_UP))
                            .debtPayment(debtPayment.setScale(2, RoundingMode.HALF_UP))
                            .remainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_UP))
                            .build()
            );
        }

        log.debug("Payment schedule calculated: {}", paymentSchedule); // Логирование завершения расчета графика платежей
        return paymentSchedule;
    }

    private BigDecimal calculateInterestPayment(BigDecimal remainingDebt, BigDecimal rate) {
        return remainingDebt.multiply(rate.divide(BigDecimal.valueOf(12 * 100), 7, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDebtPayment(BigDecimal totalPayment, BigDecimal interestPayment) {
        return totalPayment.subtract(interestPayment).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRemainingDebt(BigDecimal remainingDebt, BigDecimal debtPayment) {
        return remainingDebt.subtract(debtPayment).setScale(2, RoundingMode.HALF_UP);
    }
}
