package ru.kishko.statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestPropertySource(properties = {
        "otel.sdk.disabled=true",
        "otel.traces.exporter=none",
        "otel.metrics.exporter=none"
})

class StatementApplicationTests {

    @Test
    void contextLoads() {
    }

}
