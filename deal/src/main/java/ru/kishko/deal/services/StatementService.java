package ru.kishko.deal.services;

import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.ApplicationStatus;
import ru.kishko.openapi.model.ChangeType;
import ru.kishko.openapi.model.LoanOfferDto;

import java.util.List;
import java.util.UUID;

public interface StatementService {
    List<Statement> getAllStatements();

    Statement createStatement(Client client);

    Statement getStatementById(UUID statementId);

    void updateStatement(Statement statement, LoanOfferDto request);

    Statement updateStatusAndStatusHistory(Statement statement, ApplicationStatus applicationStatus, ChangeType changeType);

    void updateStatementByCreditInfo(Statement statement, Credit credit);

    void updateStatementSesCode(String statementId);
}
