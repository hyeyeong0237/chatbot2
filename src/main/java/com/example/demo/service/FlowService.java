package com.example.demo.service;


import com.example.demo.dto.FlowDto;
import com.example.demo.dto.StepDto;
import com.example.demo.dto.request.FlowCreateDto;
import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.repository.FlowRepository;
import com.example.demo.repository.step.MessageStepRepository;
import com.example.demo.repository.step.StepRepository;
import com.example.demo.repository.step.WebSiteStepRepository;
import com.example.demo.type.StepType;
import lombok.RequiredArgsConstructor;
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

    public FlowDto getFlow(long flowId){
        Flow flow = flowRepository.findById(flowId).orElseThrow(EntityNotFoundException::new);
        return FlowDto.toDto(flow);
    }


    //플로우 생
    @javax.transaction.Transactional
    public Flow create(FlowCreateDto flowCreateDto) {

        Flow flow = Flow.builder()
                .name(flowCreateDto.getName())
                .build();
        flowRepository.save(flow);

        for(StepDto stepDto : flowCreateDto.getSteps()){
            if(isNull(stepDto.getStepId())){
                createStep(flow, stepDto);
            }
//            else{
//                updateStep(flow, stepDto);
//            }
        }


        return flow;
    }

    private void createStep(Flow flow, StepDto stepDto) {

        if(stepDto.getStepType() == StepType.MESSAGE){
            MessageStep messageStep = MessageStep.builder()
                    .flow(flow)
                    .name(stepDto.getStepName())
                    .text((stepDto.getText()))
                    .build();

            messageStep.setFlow(flow);
            messageStepRepository.save(messageStep);

        }else if(stepDto.getStepType()== StepType.WEBSITE){
            WebSiteStep webSiteStep = WebSiteStep.builder()
                    .flow(flow)
                    .name(stepDto.getStepName())
                    .url(stepDto.getUrl())
                    .build();
            webSiteStep.setFlow(flow);
            webSiteStepRepository.save(webSiteStep);
        }


    }


}
