package com.example.demo.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

public class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final int MARIADB_PORT = 3306;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        DockerComposeContainer container = new DockerComposeContainer(new File("src/integrationTest/resources/test-database-compose.yml"))
                .withExposedService("test", MARIADB_PORT, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

        container.start();
    }
}
