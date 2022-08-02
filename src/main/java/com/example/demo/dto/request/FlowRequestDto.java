package com.example.demo.dto.request;

import com.example.demo.dto.FlowDto;
import com.example.demo.dto.StepDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowRequestDto {

    @NotBlank
    private String name;

    @NotEmpty
    private List<StepDto> steps;


    public static FlowDto toFlowDto(FlowRequestDto flowRequestDto){
        return FlowDto.builder()
                .flowName(flowRequestDto.name)
                .steps(flowRequestDto.steps)
                .build();
    }


}
