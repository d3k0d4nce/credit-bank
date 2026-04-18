package ru.kishko.gateway.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class LoanMetrics {

    private final Counter applicationsCounter;

    private final Counter issuedCounter;

    public LoanMetrics(MeterRegistry registry) {
        this.applicationsCounter = Counter.builder("loan_applications_total")
                .description("Всего заявок на кредит")
                .register(registry);

        this.issuedCounter = Counter.builder("loan_issued_total")
                .description("Всего выданных кредитов")
                .register(registry);
    }

    public void incrementApplications() {
        applicationsCounter.increment();
    }

    public void incrementIssued() {
        issuedCounter.increment();
    }
}