package ru.kishko.deal.services.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Statement;
import ru.kishko.deal.exceptions.StatementNotFoundException;
import ru.kishko.deal.repositories.StatementRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private StatementServiceImpl statementService;

    Statement expectedStatement;
    Client request;
    UUID statementId;

    @BeforeEach
    void setUp() {

        statementId = UUID.randomUUID();

        request = Client.builder()
                .email("kishko.2003@list.ru")
                .build();

        expectedStatement = Statement.builder()
                .statementId(statementId)
                .client(request)
                .build();

    }

    @Test
    void createStatement() {

        when(statementRepository.save(any(Statement.class))).thenReturn(expectedStatement);
        Statement actualStatement = statementService.createStatement(request);

        assertEquals(actualStatement, expectedStatement);

    }

    @Test
    void getStatementById_found() {
        when(statementRepository.findById(statementId)).thenReturn(Optional.of(expectedStatement));

        Statement actualStatement = statementService.getStatementById(statementId);

        assertThat(actualStatement).isEqualTo(expectedStatement);
        verify(statementRepository, times(1)).findById(statementId);
    }

    @Test
    void getStatementById_notFound() {
        when(statementRepository.findById(statementId)).thenReturn(Optional.empty());

        assertThrows(StatementNotFoundException.class, () -> statementService.getStatementById(statementId));
        verify(statementRepository, times(1)).findById(statementId);
    }

}