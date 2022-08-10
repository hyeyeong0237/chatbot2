package com.example.demo.entity.step;


import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Flow;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "step")
@DiscriminatorColumn(name = "STEP_TYPE")
@Where(clause = "deleted=false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Step extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STEP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FlOW_ID")
    @NotNull
    private Flow flow;

    @Column(name = "STEP_NAME")
    private String name;


    public Step(Flow flow, String name) {
        this.flow = flow;
        this.name = name;
    }

    public void setFlow(Flow flow){
        if(this.flow != null){
            flow.getSteps().remove(this);
        }
        this.flow = flow;
        flow.getSteps().add(this);
    }

    public void update(String name){
        this.name = name;
    }

    @Override
    public void delete(){
        super.delete();
    }

}
