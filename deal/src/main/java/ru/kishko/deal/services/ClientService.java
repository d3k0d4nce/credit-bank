package ru.kishko.deal.services;

import ru.kishko.deal.entities.Client;
import ru.kishko.openapi.model.LoanStatementRequestDto;

public interface ClientService {
    Client createClient(LoanStatementRequestDto request);
}
