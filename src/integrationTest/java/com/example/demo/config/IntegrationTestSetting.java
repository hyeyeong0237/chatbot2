package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(initializers = {TestContainerInitializer.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestSetting {

    @Autowired
    protected MockMvc mvc;

    protected static ObjectMapper objectMapper = new ObjectMapper();

}
