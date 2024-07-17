package ru.kishko.statement.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kishko.openapi.model.LoanOfferDto;
import ru.kishko.openapi.model.LoanStatementRequestDto;

import java.util.List;

@FeignClient(value = "statement", url = "${other.service.url.deal}")
public interface FeignControllerClient {

    @PostMapping(value = "/statement")
    List<LoanOfferDto> dealStatementPost(@RequestBody LoanStatementRequestDto request);

    @PostMapping(value = "/offer/select")
    void dealOfferSelectPost(@RequestBody LoanOfferDto request);
}
