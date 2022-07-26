package com.example.demo.entity.step;


import com.example.demo.entity.Flow;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MESSAGE_STEP")
@Where(clause = "deleted=false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageStep extends Step{

    private String text;

    @Builder
    public MessageStep(Flow flow, String name, String text){

        super(flow, name);
        this.text = text;
    }

    @Override
    public void setFlow(Flow flow){
        super.setFlow(flow);
    }


    public void update(String stepName, String text) {
        super.update(stepName);
        this.text = text;
    }

    @Override
    public void delete(){
        super.delete();
    }
}
