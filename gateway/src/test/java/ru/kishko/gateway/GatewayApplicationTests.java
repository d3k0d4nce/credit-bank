package ru.kishko.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "otel.traces.exporter=none",
        "otel.metrics.exporter=none"
})
class GatewayApplicationTests {

    @Test
    void contextLoads() {
    }

}
