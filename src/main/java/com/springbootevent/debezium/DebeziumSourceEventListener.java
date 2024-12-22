package com.springbootevent.debezium;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DebeziumSourceEventListener {

    private final Executor executor;
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    public DebeziumSourceEventListener(Configuration postgresConnector) {
        this.executor = Executors.newSingleThreadExecutor();
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(postgresConnector.asProperties())
                .using((success, message, error) -> {})
                .notifying(this::handleEvent)
                .build();
    }

    private void handleEvent(ChangeEvent<String, String> event) {
        this.log.info("Value: " + event.value());
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }

}
