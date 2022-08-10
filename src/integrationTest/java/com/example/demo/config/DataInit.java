package com.example.demo.config;

import com.example.demo.entity.Flow;
import com.example.demo.entity.step.MessageStep;
import com.example.demo.entity.step.WebSiteStep;
import com.example.demo.repository.FlowRepository;
import com.example.demo.repository.step.MessageStepRepository;
import com.example.demo.repository.step.WebSiteStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataInit {

    @Autowired
    private InitDataService initDataService;

    @PostConstruct
    public void init(){
        initDataService.init();
    }

    @Component
    public static class InitDataService {

        @Autowired
        private FlowRepository flowRepository;

        @Autowired
        private MessageStepRepository messageStepRepository;

        @Autowired
        private WebSiteStepRepository webSiteStepRepository;

        public static Map<String, Long> idMap = new HashMap<>();

        public void init() {

            Flow testFlow = Flow.builder()
                    .name("test flow")
                    .build();

            flowRepository.save(testFlow);
            idMap.put("test_flow_id", testFlow.getId());

            Flow testFlow2 = Flow.builder()
                    .name("test flow2")
                    .build();

            flowRepository.save(testFlow2);
            idMap.put("test_flow id_2", testFlow2.getId());


            //testflow에 저장한 스텝들
            MessageStep messageStep = MessageStep.builder()
                    .flow(testFlow)
                    .name("message step")
                    .text("hello")
                    .build();
            messageStepRepository.save(messageStep);
            idMap.put("test_messageStep_id", messageStep.getId());

            WebSiteStep webSiteStep = WebSiteStep.builder()
                    .flow(testFlow)
                    .name("website step")
                    .url("www.hello.com").
                    build();
            webSiteStepRepository.save(webSiteStep);
            idMap.put("test_websiteStep_id", webSiteStep.getId());

        }

    }
}
