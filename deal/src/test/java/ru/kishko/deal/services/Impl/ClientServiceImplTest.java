package ru.kishko.deal.services.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.mappers.ClientMapper;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    LoanStatementRequestDto request;
    Client expectedClient;

    @BeforeEach
    void setUp() {
        request = LoanStatementRequestDto.builder()
                .email("kishko.2003@list.ru")
                .build();

        expectedClient = Client.builder()
                .email(request.getEmail())
                .build();
    }

    @Test
    void createClient() {
        when(clientMapper.toClient(any(LoanStatementRequestDto.class))).thenReturn(expectedClient);
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        Client actualClient = clientService.createClient(request);

        assertEquals(actualClient, expectedClient);
    }
}