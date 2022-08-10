package com.example.demo.controller;


import com.example.demo.dto.FlowDto;
import com.example.demo.dto.request.FlowCreateDto;
import com.example.demo.dto.request.FlowRequestDto;
import com.example.demo.dto.response.FlowResponseDto;
import com.example.demo.entity.Flow;
import com.example.demo.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
public class FlowController {

    private final FlowService flowService;


    @GetMapping(value = "/{flowId}")
    public ResponseEntity<FlowDto> getFlow(@PathVariable Long flowId){

        FlowDto flow =  flowService.getFlow(flowId);
        return ResponseEntity.ok(flow);
    }

    @PostMapping
    public ResponseEntity<FlowResponseDto> createFlow(@RequestBody @Valid FlowCreateDto flowCreateDto){

        Flow flow = flowService.createFlow(flowCreateDto);

        return ResponseEntity.ok(new FlowResponseDto(flow.getId()));
    }

    @PostMapping(value = "/{flowId}")
    public ResponseEntity<FlowDto> updateFlow(@PathVariable Long flowId, @RequestBody @Valid FlowRequestDto flowRequestDto){

        FlowDto flow = flowService.updateFlow(flowId, flowRequestDto);

        return ResponseEntity.ok(flow);
    }

    @DeleteMapping(value = "{flowId}")
    public ResponseEntity<Void> deleteFlow(@PathVariable Long flowId){

       flowService.deleteFlow(flowId);

       return ResponseEntity.ok().build();
    }


}
