package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.api.dtos.LoanStatementRequestDto;
import ru.kishko.deal.jsonb.EmploymentJsonb;
import ru.kishko.deal.jsonb.PassportJsonb;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.deal.services.ClientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(LoanStatementRequestDto request) {
        log.info("Creating new client with request: {}", request);
        Client client = Client.builder()
                .email(request.getEmail())
                .birthdate(request.getBirthdate())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .passport(PassportJsonb.builder()
                        .series(request.getPassportSeries())
                        .number(request.getPassportNumber())
                        .build())
                .employment(EmploymentJsonb.builder()
                        .build())
                .build();

        client = clientRepository.save(client);
        log.info("Client created: {}", client);
        return client;
    }
}
