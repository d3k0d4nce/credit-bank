package ru.kishko.deal.services;

import ru.kishko.deal.dtos.LoanStatementRequestDto;
import ru.kishko.deal.entities.Client;

public interface ClientService {
    Client createClient(LoanStatementRequestDto request);
}
