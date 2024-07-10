package ru.kishko.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.kishko.deal.entities.Client;
import ru.kishko.deal.entities.Credit;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.ClientDto;
import ru.kishko.openapi.model.CreditDto;
import ru.kishko.openapi.model.StatementDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface StatementMapper {

    StatementMapper INSTANCE = Mappers.getMapper(StatementMapper.class);

    @Mapping(target = "statementId", source = "statementId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientToDto")
    @Mapping(target = "credit", source = "credit", qualifiedByName = "creditToDto")
    @Mapping(target = "creationDate", source = "creationDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "signDate", source = "signDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    StatementDto toStatementEntityDto(Statement statement);

    @Named("clientToDto")
    ClientDto toClientEntityDto(Client client);

    @Named("creditToDto")
    CreditDto toCreditEntityDto(Credit credit);

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? OffsetDateTime.of(localDateTime, ZoneOffset.UTC) : null;
    }
}
