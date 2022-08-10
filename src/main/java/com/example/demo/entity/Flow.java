package com.example.demo.entity;

import com.example.demo.entity.step.Step;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flow")
@Where(clause = "deleted=false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flow extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLOW_ID")
    private Long id;

    @Column(name = "FlOW_NAME")
    private String name;

    @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    @Builder
    public Flow(String name){
        this.name = name;

    }
    public void update(String name){
        this.name = name;
    }

    @Override
    public void delete(){
        super.delete();
        steps.forEach(Step::delete);


    }





}
