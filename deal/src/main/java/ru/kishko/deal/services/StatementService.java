package ru.kishko.deal.services;

import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;

import java.util.UUID;

public interface StatementService {
    Statement createStatement(Client client);

    Statement getStatementById(UUID statementId);

    void updateStatement(Statement statement, LoanOfferDto request);

    void updateStatusAndStatusHistory(Statement statement);
}
