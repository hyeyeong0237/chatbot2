package com.example.demo.repository.step;

import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.repository.FlowRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class MessageStepRepositoryTest {

    @Autowired
    MessageStepRepository messageStepRepository;
    @Autowired
    FlowRepository flowRepository;

    @Test
    @DisplayName("MessageStep 저장 test")
    void testMessageStep() throws Exception {
        Flow flow = new Flow("flow1");
        flowRepository.save(flow);

        MessageStep messageStep = new MessageStep(flow, "messageStep", "hello");
        messageStep.setText("hello");

        messageStepRepository.save(messageStep);

        MessageStep savedStep = messageStepRepository.findById(messageStep.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(savedStep.getId()).isEqualTo(messageStep.getId());

    }



}