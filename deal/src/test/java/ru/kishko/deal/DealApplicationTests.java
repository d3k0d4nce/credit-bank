package ru.kishko.deal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
		"otel.sdk.disabled=true",
		"otel.traces.exporter=none",
		"otel.metrics.exporter=none"
})
class DealApplicationTests {

	@Test
	void contextLoads() {
	}

}
