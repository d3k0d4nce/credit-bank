package ru.kishko.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kishko.deal.entities.Client;
import ru.kishko.openapi.model.LoanStatementRequestDto;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "passport.series", source = "request.passportSeries")
    @Mapping(target = "passport.number", source = "request.passportNumber")
    @Mapping(target = "employment", expression = "java(new ru.kishko.openapi.model.EmploymentDto())")
    Client toClient(LoanStatementRequestDto request);

}
