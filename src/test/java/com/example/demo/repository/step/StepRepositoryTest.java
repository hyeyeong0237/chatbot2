package com.example.demo.repository.step;

import com.example.demo.entity.Flow;
import com.example.demo.entity.step.Step;
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
class StepRepositoryTest {

    @Autowired
    StepRepository stepRepository;

    @Autowired
    FlowRepository flowRepository;

    @Test
    @DisplayName("Step 저장")
    void testStep(){
        Flow flow = new Flow("flow1");
        flowRepository.save(flow);
        Step step = new Step(flow, "test3");
        stepRepository.save(step);
        Step savedStep = stepRepository.findById(step.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(savedStep.getId()).isEqualTo(step.getId());

    }





}