package ru.kishko.deal.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kishko.api.dtos.CreditDto;
import ru.kishko.api.dtos.LoanOfferDto;
import ru.kishko.api.dtos.LoanStatementRequestDto;
import ru.kishko.api.dtos.ScoringDataDto;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "deal", url = "http://localhost:8080/calculator")
public interface FeignControllerClient {

    @PostMapping(value = "/offers")
    List<LoanOfferDto> getLoanOffers(@RequestBody @Valid LoanStatementRequestDto request);

    @PostMapping(value = "/calc")
    CreditDto calculate(@RequestBody ScoringDataDto request);
}
