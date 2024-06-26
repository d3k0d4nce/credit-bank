package ru.kishko.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kishko.deal.entities.Statement;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;
import ru.kishko.openapi.model.ScoringDataDto;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    ScoringDataMapper INSTANCE = Mappers.getMapper(ScoringDataMapper.class);

    @Mapping(target = "amount", source = "statement.appliedOffer.totalAmount")
    @Mapping(target = "birthdate", source = "statement.client.birthdate")
    @Mapping(target = "firstName", source = "statement.client.firstName")
    @Mapping(target = "isInsuranceEnabled", source = "statement.appliedOffer.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "statement.appliedOffer.isSalaryClient")
    @Mapping(target = "lastName", source = "statement.client.lastName")
    @Mapping(target = "middleName", source = "statement.client.middleName")
    @Mapping(target = "passportNumber", source = "statement.client.passport.number")
    @Mapping(target = "passportSeries", source = "statement.client.passport.series")
    @Mapping(target = "term", source = "statement.appliedOffer.term")
    ScoringDataDto toScoringDataDto(FinishRegistrationRequestDto request, Statement statement);

}
