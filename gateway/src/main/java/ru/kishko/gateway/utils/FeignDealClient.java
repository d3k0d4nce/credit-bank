package ru.kishko.gateway.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kishko.openapi.model.FinishRegistrationRequestDto;

@FeignClient(value = "gateway-deal", url = "http://localhost:8081/deal")
public interface FeignDealClient {

    @PostMapping(value = "/calculate/{statementId}")
    void dealCalculateStatementIdPost(@PathVariable("statementId") String statementId,
                                      @RequestBody FinishRegistrationRequestDto request);

    @PostMapping(value = "/document/{statementId}/send")
    void sendRequestForDocument(@PathVariable("statementId") String statementId);

    @PostMapping(value = "/document/{statementId}/sign")
    void sendRequestForSignDocument(@PathVariable("statementId") String statementId);

    @PostMapping(value = "/document/{statementId}/{code}")
    void signDocument(@PathVariable("statementId") String statementId, @PathVariable("code") Integer code);
}