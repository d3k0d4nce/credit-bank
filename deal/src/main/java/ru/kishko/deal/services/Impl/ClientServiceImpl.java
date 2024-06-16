package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.deal.services.ClientService;
import ru.kishko.openapi.model.EmploymentDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;
import ru.kishko.openapi.model.PassportDto;

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
                .passport(PassportDto.builder()
                        .series(request.getPassportSeries())
                        .number(request.getPassportNumber())
                        .build())
                .employment(EmploymentDto.builder()
                        .build())
                .build();

        client = clientRepository.save(client);
        log.info("Client created: {}", client);
        return client;
    }
}
