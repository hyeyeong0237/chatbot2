package com.example.demo.dto;


import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.entity.step.Step;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.type.StepType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {

    private Flow flow;
    private Long stepId;
    private String stepName;
    private StepType stepType;
    private String text;
    private String url;


    public static StepDto toDto(Step step){
        if(step instanceof MessageStep messageStep){
            return toMessageStepDto(messageStep);
        }else if (step instanceof WebSiteStep webSiteStep){
            return toWebSiteStepDto(webSiteStep);
        }
        return new StepDto();
    }


    public static StepDto toMessageStepDto(MessageStep step){
        return StepDto.builder()
                .stepId(step.getId())
                .stepName(step.getName())
                .stepType(StepType.MESSAGE)
                .text(step.getText())
                .build();

    }

    public static StepDto toWebSiteStepDto(WebSiteStep step){
        return StepDto.builder()
                .stepId(step.getId())
                .stepName(step.getName())
                .stepType(StepType.WEBSITE)
                .url(step.getUrl())
                .build();
    }
}
