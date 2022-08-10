package com.example.demo;

import com.example.demo.config.IntegrationTestSetting;
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
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.example.demo.config.DataInit.InitDataService.idMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FlowIntegrationTest extends IntegrationTestSetting {


    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private MessageStepRepository messageStepRepository;

    @Autowired
    private WebSiteStepRepository webSiteStepRepository;

    private final String path = "/flow";

    @Test
    @DisplayName("플로우, 스텝을 생성한다. ")
    void create_flow_test() throws Exception{


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

        java.util.List<StepDto> steps = List.of(messageStepDto, websiteStepDto);


        FlowCreateDto request = FlowCreateDto.builder()
                .name("test flow")
                .steps(steps)
                .build();

        MvcResult result = mvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int createdId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        Flow flow = flowRepository.findById((long) createdId).orElse(null);
        assertEquals(flow.getName(), request.getName());
        assertNotNull(flow.getSteps());
    }

    @Test
    @DisplayName("플로우 스텝 정보를 가져온다 ")
    void get_flow_test() throws Exception {

        long flowId = idMap.get("test_flow_id");

        mvc.perform(get(path+"/{flowId}", flowId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("플로우 스텝 정보를 수정하고 새로운 스텝을 추가 한다")
    void update_flow_test() throws Exception {

        long flowId = idMap.get("test_flow_id");

        StepDto messageStepDto = StepDto.builder()
                .stepId(idMap.get("test_messageStep_id"))
                .stepName("update message step")
                .stepType(StepType.MESSAGE)
                .text("update text")
                .build();

        StepDto websiteStepDto = StepDto.builder()
                .stepId(idMap.get("test_websiteStep_id"))
                .stepName("website step")
                .stepType(StepType.WEBSITE)
                .url("www.heelo.com")
                .build();

        StepDto messageStepNewDto = StepDto.builder()
                .stepName("new step")
                .stepType(StepType.MESSAGE)
                .text("new text")
                .build();

        List<StepDto> steps = List.of(messageStepDto, websiteStepDto, messageStepNewDto);

        FlowRequestDto request = FlowRequestDto.builder()
                .name("update flow")
                .steps(steps)
                .build();

        mvc.perform(post(path+"/{flowId}", flowId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk());



    }

    @Test
    @DisplayName("플로우를 삭제한다")
    void delete_flow_test() throws Exception {

        long flowId = idMap.get("test_flow_id");

        mvc.perform(delete(path+"/{flowId}", flowId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Flow flow = flowRepository.findById(flowId).orElse(null);
        MessageStep messageStep = messageStepRepository.findById(idMap.get("test_messageStep_id")).orElse(null);
        WebSiteStep webSiteStep = webSiteStepRepository.findById(idMap.get("test_websiteStep_id")).orElse(null);

        assertNull(flow);
        assertNull(messageStep);
        assertNull(webSiteStep);



    }

}
