package com.example.demo.repository;

import com.example.demo.entity.Flow;
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
class FlowRepositoryTest {

    @Autowired
    FlowRepository flowRepository;

    @Test
    @DisplayName("Flow 저장 테스트")
    void testFlow(){
        Flow flow = new Flow("test2");

        flowRepository.save(flow);
        Flow savedFlow = flowRepository.findById(flow.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(savedFlow.getId()).isEqualTo(flow.getId());

    }

}