package com.example.demo.dto;


import com.example.demo.entity.Flow;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlowDto {

    private Long flowId;

    private String flowName;

    private List<StepDto> steps;


    public static FlowDto toDto(Flow flow){
        return FlowDto.builder()
                .flowId(flow.getId())
                .flowName(flow.getName())
                .steps(flow.getSteps().stream().map(StepDto::toDto).collect(Collectors.toList()))
                .build();
    }

}
