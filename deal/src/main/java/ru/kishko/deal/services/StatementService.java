package ru.kishko.deal.services;

import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.LoanOfferDto;

import java.util.UUID;

public interface StatementService {
    Statement createStatement(Client client);

    Statement getStatementById(UUID statementId);

    void updateStatement(Statement statement, LoanOfferDto request);

    Statement updateStatusAndStatusHistory(Statement statement);

    void updateStatementByCreditInfo(Statement statement, Credit credit);
}
