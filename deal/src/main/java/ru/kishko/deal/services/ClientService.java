package ru.kishko.deal.services;

import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

public interface ClientService {
    Client createClient(LoanStatementRequestDto request);

    void updateClientFromRequest(Statement statement, FinishRegistrationRequestDto request);
}
