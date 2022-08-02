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
import com.example.demo.repository.step.StepRepository;
import com.example.demo.repository.step.WebSiteStepRepository;
import com.example.demo.type.StepType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlowService {

    private final FlowRepository flowRepository;
    private final MessageStepRepository messageStepRepository;
    private final WebSiteStepRepository webSiteStepRepository;

    //플로우 정보 가져오기
    public FlowDto getFlow(long flowId) {
        Flow flow = flowRepository.findByWithStep(flowId).orElseThrow(EntityNotFoundException::new);
        return FlowDto.toDto(flow);
    }


    //플로우 생성
    @javax.transaction.Transactional
    public Flow createFlow(FlowCreateDto flowCreateDto) {

        Flow flow = Flow.builder()
                .name(flowCreateDto.getName())
                .build();
        flowRepository.save(flow);

        for(StepDto stepDto : flowCreateDto.getSteps()){
            if(isNull(stepDto.getStepId())){
                createStep(flow, stepDto);
            }
        }


        return flow;
    }

    //스텝 생성
    private void createStep(Flow flow, StepDto stepDto) {

        if(stepDto.getStepType() == StepType.MESSAGE){
            MessageStep messageStep = MessageStep.builder()
                    .name(stepDto.getStepName())
                    .text((stepDto.getText()))
                    .build();

            messageStep.setFlow(flow);
            messageStepRepository.save(messageStep);

        }else if(stepDto.getStepType()== StepType.WEBSITE){
            WebSiteStep webSiteStep = WebSiteStep.builder()
                    .name(stepDto.getStepName())
                    .url(stepDto.getUrl())
                    .build();

            webSiteStep.setFlow(flow);
            webSiteStepRepository.save(webSiteStep);
        }


    }

    //플로우 수정
    public FlowDto updateFlow(long flowId, FlowRequestDto flowRequestDto){
        Flow flow = flowRepository.findById(flowId).orElseThrow(EntityNotFoundException::new);

        FlowDto flowDto = FlowRequestDto.toFlowDto(flowRequestDto);

        flow.update(flowDto.getFlowName());

        updateSteps(flow, flowDto);


        return FlowDto.toDto(flow);
    }

    //스텝 수정
    private void updateSteps(Flow flow, FlowDto flowDto){

        for(StepDto stepDto : flowDto.getSteps()){
            if(isNull(stepDto.getStepId())){
                createStep(flow, stepDto);
            }
            else{
                updateStep(stepDto);
            }
        }
    }

    //스텝 수정
    private void updateStep(StepDto stepDto) {
        StepType stepType = stepDto.getStepType();

        if(stepType == StepType.MESSAGE){
            MessageStep messageStep = messageStepRepository.findById(stepDto.getStepId()).orElseThrow(EntityNotFoundException::new);
            messageStep.update(stepDto.getStepName(), stepDto.getText());
        }else if(stepType == StepType.WEBSITE){
            WebSiteStep webSiteStep = webSiteStepRepository.findById(stepDto.getStepId()).orElseThrow(EntityNotFoundException::new);
            webSiteStep.update(stepDto.getStepName(), stepDto.getUrl());
        }
    }



}
