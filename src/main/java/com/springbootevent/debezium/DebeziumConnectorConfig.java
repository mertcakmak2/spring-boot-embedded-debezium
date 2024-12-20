package com.springbootevent.debezium;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration postgresConnector() {

        Map<String, String> configMap = new HashMap<>();
        configMap.put("name", "pg-cdc-connector-10");
        configMap.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        configMap.put("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        configMap.put("offset.storage.file.filename", createOffsetFile());
        configMap.put("offset.flush.interval.ms", "60000");
        configMap.put("database.hostname", "localhost");
        configMap.put("database.port", "5433");
        configMap.put("database.user", "postgres");
        configMap.put("database.password", "postgres");
        configMap.put("database.dbname", "event");
        configMap.put("database.server.name", "local_debezium_pg");
        configMap.put("plugin.name", "pgoutput");
        configMap.put("schema.include.list", "public");
        //configMap.put("table.include.list", "public.posts");
        configMap.put("table.include.list", "public.*");
        configMap.put("topic.prefix", "cdc");

        return io.debezium.config.Configuration.from(configMap);
    }

    private String createOffsetFile() {
        File offsetStorageTempFile = new File("offsets_.dat");
        var filePath = offsetStorageTempFile.getAbsolutePath();
        log.info("Path: " + filePath);

        return filePath;
    }

}
