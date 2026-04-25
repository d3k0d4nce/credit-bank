package ru.kishko.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "otel.traces.exporter=none",
        "otel.metrics.exporter=none"
})
class CalculatorApplicationTests {

	@Test
	void contextLoads() {
	}

}
