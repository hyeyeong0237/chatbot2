package com.example.demo.dto.request;


import com.example.demo.dto.StepDto;
import com.example.demo.entity.step.Step;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowCreateDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    private List<StepDto> steps;
}
