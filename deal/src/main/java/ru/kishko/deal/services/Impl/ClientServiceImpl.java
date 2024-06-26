package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.mappers.ClientMapper;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.deal.services.ClientService;
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
}
