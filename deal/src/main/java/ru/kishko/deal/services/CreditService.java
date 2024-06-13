package ru.kishko.deal.services;

import ru.kishko.deal.dtos.CreditDto;
import ru.kishko.deal.entities.Credit;

public interface CreditService {
    Credit createCredit(CreditDto creditDto);
}
