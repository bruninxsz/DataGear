package com.example.backend_java;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.RestClient;

import com.example.backend_java.config.RestClientConfig;
import com.example.backend_java.model.Telemetria;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

class RestClientAndMappingTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(RestClientConfig.class);

    @Test
    void restClientBuilderBeanShouldBeAvailable() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(RestClient.Builder.class);
        });
    }

    @Test
    void statusTelemetriaShouldBeStoredAsString() throws NoSuchFieldException {
        Field field = Telemetria.class.getDeclaredField("status_telemetria");
        Enumerated enumerated = field.getAnnotation(Enumerated.class);

        assertThat(enumerated).isNotNull();
        assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
    }
}
