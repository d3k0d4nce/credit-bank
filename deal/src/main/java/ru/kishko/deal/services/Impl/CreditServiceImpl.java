package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.mappers.CreditMapper;
import ru.kishko.deal.repositories.CreditRepository;
import ru.kishko.deal.services.CreditService;
import ru.kishko.openapi.model.CreditDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CreditMapper creditMapper;

    @Override
    public Credit createCredit(CreditDto creditDto) {
        log.info("Creating new credit with data: {}", creditDto);
        Credit credit = creditMapper.toCredit(creditDto);
        credit = creditRepository.save(credit);
        log.info("Credit created: {}", credit);
        return credit;
    }
}
