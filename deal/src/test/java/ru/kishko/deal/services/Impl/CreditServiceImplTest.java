package ru.kishko.deal.services.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.mappers.CreditMapper;
import ru.kishko.deal.repositories.CreditRepository;
import ru.kishko.openapi.model.CreditDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CreditMapper creditMapper;

    @InjectMocks
    private CreditServiceImpl creditService;

    CreditDto request;
    Credit expectedCredit;

    @BeforeEach
    void setUp() {

        request = CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .build();

        expectedCredit = Credit.builder()
                .amount(request.getAmount())
                .build();

    }

    @Test
    void createCredit() {
        when(creditMapper.toCredit(any(CreditDto.class))).thenReturn(expectedCredit);
        when(creditRepository.save(any(Credit.class))).thenReturn(expectedCredit);
        Credit actualCredit = creditService.createCredit(request);

        assertEquals(actualCredit, expectedCredit);
    }
}