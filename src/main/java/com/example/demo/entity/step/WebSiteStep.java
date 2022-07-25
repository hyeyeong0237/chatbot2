package com.example.demo.entity.step;

import com.example.demo.entity.Flow;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WEBSITE_STEP")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebSiteStep extends Step{

    private String url;

    @Builder
    public WebSiteStep(Flow flow, String name, String url) {
        super(flow, name);
        this.url = url;
    }

    @Override
    public void setFlow(Flow flow){
        super.setFlow(flow);
    }

    public void setUrl(String url){
        this.url = url;
    }

}
