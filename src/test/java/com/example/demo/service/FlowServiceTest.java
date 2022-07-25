package com.example.demo.service;

import com.example.demo.dto.StepDto;
import com.example.demo.dto.request.FlowCreateDto;
import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.repository.FlowRepository;
import com.example.demo.repository.step.MessageStepRepository;
import com.example.demo.repository.step.WebSiteStepRepository;
import com.example.demo.type.StepType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class FlowServiceTest {

    @Mock
    private FlowRepository flowRepository;

    @Mock
    private MessageStepRepository messageStepRepository;

    @Mock
    private WebSiteStepRepository webSiteStepRepository;

    @InjectMocks
    private FlowService flowService;


    @Test
    @DisplayName("플로우 생성")
    void create_new_flow_test() throws Exception {

        //given
        StepDto messageStepDto = StepDto.builder()
                .stepName("message step")
                .stepType(StepType.MESSAGE)
                .text("hello")
                .build();

        StepDto websiteStepDto = StepDto.builder()
                .stepName("website step")
                .stepType(StepType.WEBSITE)
                .url("www.hello.com")
                .build();

        List<StepDto> steps = List.of(messageStepDto, websiteStepDto);


        FlowCreateDto flowCreateDto = FlowCreateDto.builder()
                .name("test flow")
                .steps(steps)
                .build();

        //when
        Flow flowResult = flowService.create(flowCreateDto);
        MessageStep messageStep = (MessageStep) flowResult.getSteps().get(0);
        WebSiteStep webSiteStep = (WebSiteStep) flowResult.getSteps().get(1);

        //then
        assertEquals(flowResult.getName(), flowCreateDto.getName());

        assertEquals(messageStep.getName(), messageStepDto.getStepName());
        assertEquals(messageStep.getText(), messageStepDto.getText());

        assertEquals(webSiteStep.getName(), websiteStepDto.getStepName());
        assertEquals(webSiteStep.getUrl(), websiteStepDto.getUrl());

    }


}