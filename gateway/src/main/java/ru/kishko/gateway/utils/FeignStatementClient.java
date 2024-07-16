package ru.kishko.gateway.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

@FeignClient(value = "gateway-statement", url = "http://localhost:8082/statement")
public interface FeignStatementClient {

    @PostMapping
    List<LoanOfferDto> getLoanOffers(@RequestBody LoanStatementRequestDto request);

    @PostMapping(value = "/offer")
    void applyOffer(@RequestBody LoanOfferDto request);
}