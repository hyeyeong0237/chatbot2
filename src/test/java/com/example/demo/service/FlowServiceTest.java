package com.example.demo.service;

import com.example.demo.dto.FlowDto;
import com.example.demo.dto.StepDto;
import com.example.demo.dto.request.FlowCreateDto;
import com.example.demo.dto.request.FlowRequestDto;
import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.repository.FlowRepository;
import com.example.demo.repository.step.MessageStepRepository;
import com.example.demo.repository.step.WebSiteStepRepository;
import com.example.demo.type.StepType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


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

    private Long flowId = 1L;
    private Flow flow;
    private MessageStep messageStep;
    private WebSiteStep webSiteStep;


    private
    @BeforeEach
    void init(){


        flow = Flow.builder()
                .name("test flow")
                .build();

        flow.setId(flowId);

       messageStep = MessageStep.builder()
                .name("message step")
                .text("hello")
                .build();

       messageStep.setId(1L);
       messageStep.setFlow(flow);

       webSiteStep = WebSiteStep.builder()
                .name("website step")
                .url("www.hello.com")
                .build();

       webSiteStep.setId(2L);
       webSiteStep.setFlow(flow);

    }


    @Test
    @DisplayName("플로우 생성 한다 ")
    void create_new_flow_test() throws Exception {

        //given다
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
        Flow flowResult = flowService.createFlow(flowCreateDto);
        MessageStep messageStep = (MessageStep) flowResult.getSteps().get(0);
        WebSiteStep webSiteStep = (WebSiteStep) flowResult.getSteps().get(1);

        //then
        assertEquals(flowResult.getName(), flowCreateDto.getName());

        assertEquals(messageStep.getName(), messageStepDto.getStepName());
        assertEquals(messageStep.getText(), messageStepDto.getText());

        assertEquals(webSiteStep.getName(), websiteStepDto.getStepName());
        assertEquals(webSiteStep.getUrl(), websiteStepDto.getUrl());

    }

    @Test
    @DisplayName("플로우 정보 가져오기")
    void get_flow_test() throws Exception {

        StepDto messageStepDto = StepDto.builder()
                .stepId(1L)
                .stepName("message step")
                .stepType(StepType.MESSAGE)
                .text("hello")
                .build();

        StepDto websiteStepDto = StepDto.builder()
                .stepId(2L)
                .stepName("website step")
                .stepType(StepType.WEBSITE)
                .url("www.hello.com")
                .build();

        List<StepDto> steps = List.of(messageStepDto, websiteStepDto);

        FlowDto flowDto = FlowDto.builder()
                .flowId(flowId)
                .flowName("test flow")
                .steps(steps)
                .build();

        when(flowRepository.findByWithStep(flowId)).thenReturn(Optional.ofNullable(flow));

        FlowDto flowResult = flowService.getFlow(flowId);

        StepDto messageStep = flowResult.getSteps().get(0);
        StepDto webSiteStep = flowResult.getSteps().get(1);

        assertEquals(flowDto.getFlowId(), flowResult.getFlowId());
        assertEquals(flowDto.getFlowName(), flowResult.getFlowName());

        assertEquals(messageStepDto.getStepId(), messageStep.getStepId());
        assertEquals(messageStepDto.getStepName(), messageStep.getStepName());
        assertEquals(messageStepDto.getStepType(), messageStep.getStepType());


        assertEquals(websiteStepDto.getStepId(), webSiteStep.getStepId());
        assertEquals(websiteStepDto.getStepName(), webSiteStep.getStepName());
        assertEquals(websiteStepDto.getStepType(), webSiteStep.getStepType());



    }

    @Test
    @DisplayName("플로우 정보 수정하기 ")
    void update_flow_test(){

        StepDto messageStepDto = StepDto.builder()
                .stepId(1L)
                .stepName("update message step")
                .stepType(StepType.MESSAGE)
                .text("update text")
                .build();

        StepDto websiteStepDto = StepDto.builder()
                .stepId(2L)
                .stepName("update website step")
                .stepType(StepType.WEBSITE)
                .url("www.heelo.com")
                .build();

        List<StepDto> steps = List.of(messageStepDto, websiteStepDto);

        FlowRequestDto requestDto = FlowRequestDto.builder()
                .name("update flow")
                .steps(steps)
                .build();


        when(flowRepository.findById(flowId)).thenReturn(Optional.ofNullable(flow));
        when(messageStepRepository.findById(1L)).thenReturn(Optional.ofNullable(messageStep));
        when(webSiteStepRepository.findById(2L)).thenReturn(Optional.ofNullable(webSiteStep));


        //when
        FlowDto result = flowService.updateFlow(flowId, requestDto);

        //then
        assertEquals(requestDto.getName(), result.getFlowName());

        assertEquals(messageStep.getName(), messageStepDto.getStepName());
        assertEquals(messageStep.getText(), messageStepDto.getText());

        assertEquals(webSiteStep.getName(), websiteStepDto.getStepName());
        assertEquals(webSiteStep.getUrl(), websiteStepDto.getUrl());

    }


}