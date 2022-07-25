package com.example.demo.repository.step;

import com.example.demo.entity.Flow;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.repository.FlowRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
class WebSiteRepositoryTest {

    @Autowired
    WebSiteStepRepository webSiteStepRepository;

    @Autowired
    FlowRepository flowRepository;


    @Test
    @DisplayName("WebSiteStep 저장 test")
    void testWebSiteStep(){
        Flow flow = new Flow("flow1");
        flowRepository.save(flow);

        WebSiteStep webSiteStep = new WebSiteStep(flow, "websiteStep", "www.hello.com");


        webSiteStepRepository.save(webSiteStep);

        WebSiteStep savedStep = webSiteStepRepository.findById(webSiteStep.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(savedStep.getId()).isEqualTo(webSiteStep.getId());

    }

}