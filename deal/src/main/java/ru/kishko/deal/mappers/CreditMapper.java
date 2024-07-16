package ru.kishko.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kishko.deal.entities.Credit;
import ru.kishko.openapi.model.CreditDto;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    @Mapping(target = "creditId", ignore = true)
    @Mapping(target = "creditStatus", constant = "CALCULATED")
    Credit toCredit(CreditDto creditDto);
}
