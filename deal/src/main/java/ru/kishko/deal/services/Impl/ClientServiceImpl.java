package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.mappers.ClientMapper;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.deal.services.ClientService;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client createClient(LoanStatementRequestDto request) {
        log.info("Creating new client with request: {}", request);
        Client client = clientMapper.toClient(request);
        log.info("Client from request: {}", client);
        client = clientRepository.save(client);
        log.info("Client created: {}", client);
        return client;
    }

    @Override
    public void updateClientFromRequest(Statement statement, FinishRegistrationRequestDto request) {
        log.info("Updating client for statementId: {}", statement.getStatementId());
        Client client = statement.getClient();
        updateClient(client, request);
        clientRepository.save(client);
        log.info("Client updated for statementId: {}", statement.getStatementId());
    }

    private void updateClient(Client client, FinishRegistrationRequestDto request) {
        log.info("Updating client data: {}", request);
        client.setGender(request.getGender());
        client.setMaritalStatus(request.getMaritalStatus());
        client.setDependentAmount(request.getDependentAmount());
        client.setAccountNumber(request.getAccountNumber());
        client.setEmployment(request.getEmployment());
        client.getPassport().setIssueBranch(request.getPassportIssueBranch());
        client.getPassport().setIssueDate(request.getPassportIssueDate());
    }
}
