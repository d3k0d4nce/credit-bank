package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.api.dtos.CreditDto;
import ru.kishko.api.enums.CreditStatus;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.repositories.CreditRepository;
import ru.kishko.deal.services.CreditService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Override
    public Credit createCredit(CreditDto creditDto) {
        log.info("Creating new credit with data: {}", creditDto);
        Credit credit = Credit.builder()
                .creditStatus(CreditStatus.CALCULATED)
                .amount(creditDto.getAmount())
                .psk(creditDto.getPsk())
                .isInsuranceEnabled(creditDto.getIsInsuranceEnabled())
                .isSalaryClient(creditDto.getIsSalaryClient())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .rate(creditDto.getRate())
                .term(creditDto.getTerm())
                .build();

        credit = creditRepository.save(credit);
        log.info("Credit created: {}", credit);
        return credit;
    }
}
