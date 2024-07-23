package ru.kishko.deal.services;

import ru.kishko.deal.entities.Credit;
import ru.kishko.openapi.model.CreditDto;

public interface CreditService {
    Credit createCredit(CreditDto creditDto);
}
