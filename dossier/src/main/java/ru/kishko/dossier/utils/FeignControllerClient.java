package ru.kishko.dossier.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dossier", url = "${other.service.url.deal}")
public interface FeignControllerClient {

    @PutMapping(value = "/dossier/statement/status")
    void updateApplicationStatus(@RequestParam String statementId);

    @GetMapping(value = "/dossier/statement")
    Integer getSesCodeByStatementId(@RequestParam String statementId);
}
