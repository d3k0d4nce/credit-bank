package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.api.enums.ApplicationStatus;
import ru.kishko.api.enums.ChangeType;
import ru.kishko.deal.jsonb.StatusHistoryJsonb;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.exceptions.StatementNotFoundException;
import ru.kishko.deal.repositories.StatementRepository;
import ru.kishko.deal.services.StatementService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Override
    public Statement createStatement(Client client) {
        log.info("Creating new statement for client: {}", client);
        Statement statement = Statement.builder()
                .client(client)
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .statusHistory(Collections.singletonList(createStatusHistoryJsonb(ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC)))
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .build();
        statement = statementRepository.save(statement);
        log.info("Statement created: {}", statement);
        return statement;
    }

    @Override
    public Statement getStatementById(UUID statementId) {
        log.info("Getting statement by id: {}", statementId);
        return statementRepository.findById(statementId).orElseThrow(
                () -> new StatementNotFoundException("There is no statement with id: " + statementId)
        );
    }

    @Override
    public void updateStatement(Statement statement, LoanOfferDto request) {
        log.info("Updating statement: {}", statement);
        StatusHistoryJsonb statusHistoryJsonb = createStatusHistoryJsonb(ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);
        addStatusHistory(statement, statusHistoryJsonb);
        statement.setApplicationStatus(ApplicationStatus.valueOf(statusHistoryJsonb.getStatus()));
        statement.setAppliedOffer(request);
        statementRepository.save(statement);
        log.info("Statement updated.");
    }

    @Override
    public void updateStatusAndStatusHistory(Statement statement) {
        log.info("Updating statement status and history: {}", statement);
        StatusHistoryJsonb statusHistoryJsonb = createStatusHistoryJsonb(ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        addStatusHistory(statement, statusHistoryJsonb);
        statement.setApplicationStatus(ApplicationStatus.valueOf(statusHistoryJsonb.getStatus()));
        statementRepository.save(statement);
        log.info("Statement status and history updated.");
    }

    private StatusHistoryJsonb createStatusHistoryJsonb(ApplicationStatus applicationStatus, ChangeType changeType) {
        log.info("Creating new status history entry: status = {}, changeType = {}", applicationStatus, changeType);
        return StatusHistoryJsonb.builder()
                .status(applicationStatus.name())
                .changeType(changeType)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    private void addStatusHistory(Statement statement, StatusHistoryJsonb statusHistoryJsonb) {
        log.info("Adding status history entry to statement: {}", statement);
        List<StatusHistoryJsonb> list = statement.getStatusHistory();
        list.add(statusHistoryJsonb);
        statement.setStatusHistory(list);
    }
}
